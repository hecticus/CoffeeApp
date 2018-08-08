import { ActivatedRoute, Router } from '@angular/router';
import { BaseService } from '../../core/base.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FilterService } from '../../core/utils/filter/filter.service';
import { FormGroup } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';
import { ProviderType } from '../../core/models/provider-type';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Status } from '../../core/models/status';
import { StatusProviderService } from '../status/status-provider.service';



@Component({
	selector: 'app-provider.list',
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Proveedores</h2>

		<div class="row">
				<div class="field filter">
					<input matInput (keyup)="applyFilter($event.target.value)" placeholder="Filtrar">
				</div>

				<div class="field">
				<!-- <mat-select placeholder="Estatus" [(ngModel)]="selected">-->
					<mat-select placeholder="Tipo de Proveedor" [(ngModel)]="filterService.filter['providerType']"
																(change)="filterService.put('providerType',
																$event.target.value); changeProviderType($event.target.value)">
						<mat-option>Ninguna</mat-option>
						<mat-option *ngFor="let pt of provType" [value]="pt.id"> {{pt.nameProviderType}} </mat-option>
					</mat-select>
				</div>

				<div class="field">
					<mat-select placeholder="Estatus" [(ngModel)]="filterService.filter['statusProvider']"
														(change)="filterService.put('statusProvider',
														$event.target.value); changeStatus($event.target.value)">
						<mat-option>Ninguna</mat-option>
						<mat-option *ngFor="let s of status" [value]="s.id"> {{s.name}} </mat-option>
					</mat-select>
				</div>

				<div class="container-button-filter">
					<button class="btn-icon" title="Search" type="button" (click)="list(0)">
						<i class="material-icons">search</i>
					</button>
				</div>
		</div>

		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" type="button" (click)="create()">
					<i class="material-icons">add</i>
				</button>
				<!-- <button class="btn-icon" type="button">
				<button class="btn-icon" title="Delete" type="button"
				(click)="confirmDelete = false" *ngIf="tableService.getSelectedsLength() > 0">
					<i class="material-icons">delete</i>
				</button> -->
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
				<ng-container matColumnDef="providerType.nameProviderType">
					<th class="table-header" mat-header-cell *matHeaderCellDef>Tipo de Proveedor</th>
					<td mat-cell *matCellDef="let provider"> {{provider.providerType?.nameProviderType || '-'}} </td>
				</ng-container>

				<!-- Position nitProvider -->
				<ng-container matColumnDef="nitProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>RUC</th>
					<td mat-cell *matCellDef="let provider"> {{provider.nitProvider || '-'}}</td>
				</ng-container>

				<!-- Position Namme -->
				<ng-container matColumnDef="nameProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header><span>Nombre</span></th>
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

				<!-- Position Status -->
				<ng-container matColumnDef="statusProvider.name">
					<th class="table-header" mat-header-cell *matHeaderCellDef>Estatus</th>
					<td mat-cell *matCellDef="let provider"> {{provider.statusProvider?.name || '-'}} </td>
				</ng-container>

				<!-- Position statusProvider
				<ng-container matColumnDef="deleted">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Deleted</th>
					<td mat-cell *matCellDef="let provider">
						<div *ngIf="provider.deleted" >Inactivo</div>
						<div *ngIf="!provider.deleted">Activo</div>
					</td>
				</ng-container>-->

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
	  			<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" class="element-row"  (click)="read(row.id)"></tr>
			</table>
			<mat-paginator [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>
		</div>
		<div class="field">
			{{selected+ " " + filterService.filter['providerType']}}
		</div>

	`
})
export class ProviderListComponent implements OnInit {
	form: FormGroup;
	provType: ProviderType[];
	providers: Provider[];
	provider: Provider;
	status: Status;
	selected: number;
	selectedStatus: number;

	// Order Columns Display
	columnsToDisplay = ['select', 'providerType.nameProviderType', 'statusProvider.name', 'nameProvider', 'nitProvider',
						'addressProvider', 'emailProvider',
						'contactNameProvider', 'numberProvider'];

	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [ 15, 30, 60];

	dataSource = new MatTableDataSource<Provider>();

	// Defione Selection
	selection = new SelectionModel<Provider>(true, []);

	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatPaginator) paginator: MatPaginator;
	constructor(
		private providerService: ProviderService,
		private providerTypeService: ProviderTypeService,
		private statusProviderService: StatusProviderService,
		public filterService: FilterService,
		private router: Router,
		private activatedRoute: ActivatedRoute,
	) { }

	ngOnInit() {
		this.dataSource.sort = this.sort;
		this.dataSource.paginator = this.paginator;

		this.filter();
		this.list();

	}

	filter() {
		// this.providerService.getAll().subscribe(
		// 	data => {
		// 		this.dataSource.data = data['result'];
		// });

		let httpParams = BaseService.jsonToHttpParams({
			collection: 'id,nameProviderType'
		});

		this.providerTypeService.getAll(httpParams).subscribe(
			data => {
				this.provType = data['result'];
		});

		this.statusProviderService.getAll().subscribe(
			data => {
				this.status = data['result'];
				console.log(this.status);
			}
		);
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
	}

	list(page = 0) {
		let httpParams = BaseService.jsonToHttpParams({
			// sort: this.table.sort,
			collection: 'id, nameProvider, nitProvider, addressProvider, emailProvider, contactNameProvider, numberProvider,' +
						'createdAt, providerType(id, nameProviderType), statusProvider(id, name))',
			// 'pager.index': page,
			// 'pager.size': this.table.pager.pageSize,
			// ...this.filterService.filter
			// 'providerType': this.selected,
			// 'statusProvider': this.selectedStatus,
			...this.filterService.filter
		});
		console.log('$event');
		console.log(this.filterService.filter);

		this.providerService.getAll(httpParams).subscribe(
			data => {
				this.dataSource.data = data['result'];
				console.log(this.dataSource);
		});
	}

	changeProviderType(idProviderType: any ) {
		console.log(idProviderType);
	}

	changeStatus(idProviderType: any ) {
		console.log(idProviderType);
	}
}
