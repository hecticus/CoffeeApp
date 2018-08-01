
import { Params, Router, ActivatedRoute } from '@angular/router';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { ProviderService } from './provider.service';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Provider, ViewChild } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { FilterService } from '../../core/filter/filter.service';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
	selector: 'app-provider.list',
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Proveedores</h2>
		<div class="filter row">
		<!--	<div class="field">
				<mat-select placeholder="Provider Type" [(ngModel)]="seler" name="pt">
					<mat-option>-- None --</mat-option>
					<mat-option *ngFor="let pt of provType" [value]="pt.id" >
			{{pt.nameProviderType}}
					</mat-option>
				</mat-select>
			</div>-->
			<div class="field">
				<input matInput (keyup)="applyFilter($event.target.value)" placeholder="Search">
			</div>
			<div class="container-button-filter">
				<button class="btn-icon" title="Search" type="button" (click)="manejo($event)">
					<i class="material-icons">search</i>
				</button>
			</div>
		</div>

		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" type="button" (click)="create()">
					<i class="material-icons">add</i>
				</button>
				<button class="btn-icon" type="button"> <!--
				<button class="btn-icon" title="Delete" type="button" (click)="confirmDelete = false" *ngIf="tableService.getSelectedsLength() > 0">-->
					<i class="material-icons">delete</i>
				</button>
			</div>
		</div>

		<div class="mat-elevation-z8" >
			<!-- Definition table -->
			<table class="table" mat-table [dataSource]="dataSource" matSort class="mat-elevation-z8">

				<!-- Checkbox Column -->
				<ng-container matColumnDef="select">
				  <th mat-header-cell *matHeaderCellDef>
					<mat-checkbox (change)="$event ? masterToggle() : null"
								  [checked]="selection.hasValue() && isAllSelected()"
								  [indeterminate]="selection.hasValue() && !isAllSelected()">
					</mat-checkbox>
				  </th>
				  <td mat-cell *matCellDef="let row">
					<mat-checkbox (click)="$event.stopPropagation()"
								  (change)="$event ? selection.toggle(row) : null"
								  [checked]="selection.isSelected(row)">
					</mat-checkbox>
				  </td>
				</ng-container>

				<!-- Position ProviderType -->
				<ng-container matColumnDef="provider.providerType.nameProviderType">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Tipo de Proveedor</th>
					<td mat-cell *matCellDef="let provider"> {{provider.providerType?.nameProviderType || '-'}} </td>
				</ng-container>

				<!-- Position nitProvider -->
				<ng-container matColumnDef="nitProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>RUC</th>
					<td mat-cell *matCellDef="let provider"> {{provider.nitProvider || '-'}}</td>
				</ng-container>

				<!-- Position Namme -->
				<ng-container matColumnDef="nameProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef><span>Nombre</span></th>
					<td mat-cell *matCellDef="let provider"> {{provider.nameProvider || '-'}} </td>
				</ng-container>

				<!-- Position Address -->
				<ng-container matColumnDef="addressProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Dirección</th>
					<td mat-cell *matCellDef="let provider"> {{provider.addressProvider || '-'}} </td>
				</ng-container>

				<!-- Position numberProvider -->
				<ng-container matColumnDef="numberProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Número Telefónico</th>
					<td mat-cell *matCellDef="let provider"> {{provider.numberProvider || '-'}} </td>
				</ng-container>

				<!-- Position emailProvider -->
				<ng-container matColumnDef="emailProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Correo Electrónico</th>
					<td mat-cell *matCellDef="let provider"> {{provider.emailProvider || '-'}} </td>
				</ng-container>

				<!-- Position contactNameProvider -->
				<ng-container matColumnDef="contactNameProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Nombre de Contacto</th>
					<td mat-cell *matCellDef="let provider"> {{provider.contactNameProvider || '-'}} </td>
				</ng-container>

				<!-- Position statusProvider -->
				<ng-container matColumnDef="statusProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Status</th>
					<td mat-cell *matCellDef="let provider"> {{provider.statusProvider?.name || '-'}} </td>
				</ng-container>

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
	  			<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" class="element-row"  (click)="read(row.id)"></tr>
			</table>
			<mat-paginator [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>
		</div>

	`
})
export class ProviderListComponent implements OnInit {
	form: FormGroup;
	provType: ProviderType[];
	providers: Provider[];

	// Order Columns Display
	columnsToDisplay = ['select', 'nameProvider', 'nitProvider', 'provider.providerType.nameProviderType',
						'statusProvider', 'addressProvider', 'emailProvider',
						'contactNameProvider', 'numberProvider'];
	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [5, 10, 20];

	dataSource = new MatTableDataSource<Provider>();

	// Defione Selection
	selection = new SelectionModel<Provider>(true, []);
	// const initialSelection = [];
	// const allowMultiSelect = true;
	// selection = new SelectionModel<Provider>(allowMultiSelect, initialSelection);

	seler = 4;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatPaginator) paginator: MatPaginator;
	constructor(
		private providerService: ProviderService,
		private providerTypeService: ProviderTypeService,
		private router: Router,
		private activatedRoute: ActivatedRoute,
	) { }

	ngOnInit() {

		this.dataSource.sort = this.sort;
		this.dataSource.paginator = this.paginator;

		this.providerTypeService.getAll().subscribe(
			data => {
				this.provType = data['result'];
			console.log(this.provType);
		});

		this.providerService.getAll().subscribe(
			data => {
				this.providers = data['result'];
				this.dataSource.data = data['result'];
			console.log(this.dataSource);
		});

	}

	create() {
		this.router.navigate(['./create'], {relativeTo: this.activatedRoute});
	}

	manejo($event: any) {
		console.log($event);
	}

	/** Whether the number of selected elements matches the total number of rows. */
	isAllSelected() {
		const numSelected = this.selection.selected.length;
		const numRows = this.dataSource.data.length;
		return numSelected === numRows;
		}

	/** Selects all rows if they are not all selected; otherwise clear selection. */
	masterToggle() {
	this.isAllSelected() ?
		this.selection.clear() :
		this.dataSource.data.forEach(row => this.selection.select(row));
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	read(id: number) {
		this.router.navigate(['./' + id], {relativeTo: this.activatedRoute});
		console.log(id);
	}
}
