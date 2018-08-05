
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Provider, ViewChild } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { LotService } from './lot.service';
import { BaseService } from '../../core/base.service';

@Component({
	styleUrls: ['./lot.component.css'],
	template: `
		<h2 class="title">Lotes</h2>
		<div class="filter row">
			<!--<div class="field">
					<mat-select placeholder="Type Farm" [(ngModel)]="selectedValue" name="food">
						<mat-option>-- None --</mat-option>
						<mat-option *ngFor="let food of foods" [value]="food.value">
							{{food.viewValue}}
						</mat-option>
					</mat-select>
				</div> -->
			<div class="field">
				<input matInput (keyup)="applyFilter($event.target.value)" placeholder="Filtrar">
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
				<!--<button class="btn-icon" type="button">
				<button class="btn-icon" title="Delete" type="button" (click)="confirmDelete = false" *ngIf="tableService.getSelectedsLength() > 0">
					<i class="material-icons">delete</i>
				</button>-->
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

				<!-- Position Namme -->
				<ng-container matColumnDef="nameLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef><span (click)="test()">Nombre</span></th>
					<td mat-cell *matCellDef="let lot"> {{lot.nameLot}} </td>
				</ng-container>

				<!-- Position Farm -->
				<ng-container matColumnDef="farm.nameFarm">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Granja</th>
					<td mat-cell *matCellDef="let lot"> {{lot.farm.nameFarm}} </td>
				</ng-container>

				<!-- Position  Status-->
				<!-- <ng-container matColumnDef="deleted">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Status</th>
					<td mat-cell *matCellDef="let lot">
						<div *ngIf="lot.deleted" >Inactivo</div>
						<div *ngIf="!lot.deleted">Activo</div>
					</td>
				</ng-container>-->

				<!-- Position  Status-->
				<ng-container matColumnDef="areaLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Área</th>
					<td mat-cell *matCellDef="let lot"> {{lot.areaLot}} </td>
				</ng-container>

				<!-- Position  Status-->
				<ng-container matColumnDef="heighLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Altura</th>
					<td mat-cell *matCellDef="let lot"> {{lot.heighLot}} </td>
				</ng-container>

				<!-- Position  Status-->
				<ng-container matColumnDef="priceLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Precio</th>
					<td mat-cell *matCellDef="let lot"> {{lot.priceLot}} </td>
				</ng-container>

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
	  			<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" class="element-row"  (click)="read(row.id)"></tr>
			</table>
			<mat-paginator [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>
		</div>

	`
})
export class LotListComponent implements OnInit {
	form: FormGroup;
	provType: ProviderType[];
	providers: Provider[];

	// Order Columns Display
	columnsToDisplay = ['select', 'nameLot', 'farm.nameFarm', 'areaLot', 'heighLot', 'priceLot'];
	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [ 15, 30, 60];

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
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
	) { }

	ngOnInit() {

		this.dataSource.sort = this.sort;
		this.dataSource.paginator = this.paginator;

		let httpParams = BaseService.jsonToHttpParams({
			// collection: 'id ',
			deleted: '1',
		});

		this.lotService.getAll().subscribe(
			data => {
				this.dataSource.data = data['result'];
				console.log(this.dataSource.data);
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
	}
	test() {
		console.log(123456);
	}

}
