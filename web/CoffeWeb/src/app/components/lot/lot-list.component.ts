import { ActivatedRoute, Router } from '@angular/router';
import { BaseService } from '../../core/base.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Farm } from './../../core/models/farm';
import { FilterService } from '../../core/filter/filter.service';
import { Hero } from './../heroes/hero';
import { Lot } from '../../core/models/lot';
import { LotService } from './lot.service';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSort } from '@angular/material/sort';
import { SelectionModel } from '@angular/cdk/collections';
import { MatPaginator, MatTableDataSource } from '@angular/material';


@Component({
	styleUrls: ['./lot.component.css'],
	template: `
		<div class="mat-elevation-z8" >
			<!-- Definition table -->
			<table class="table" mat-table [dataSource]="lots">

				<!-- Checkbox Column -->
				<ng-container matColumnDef="select">
				    <th mat-header-cell *matHeaderCellDef>
				      	<mat-checkbox  (change)="$event ? masterToggle() : null"
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
					<th class="table-header" mat-header-cell *matHeaderCellDef><span (click)="test()">Name</span></th>
					<td mat-cell *matCellDef="let lot"> {{lot.nameLot}} </td>
				</ng-container>

				<!-- Position Id -->
				<ng-container matColumnDef="id">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Id</th>
					<td mat-cell *matCellDef="let lot"> {{lot.id}} </td>
				</ng-container>

				<!-- Position Farm -->
				<ng-container matColumnDef="farm.nameFarm">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Farm</th>
					<td mat-cell *matCellDef="let lot"> {{lot.farm.nameFarm}} </td>
				</ng-container>

				<!-- Position  Status-->
				<ng-container matColumnDef="statusLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Status</th>
					<td mat-cell *matCellDef="let lot"> {{lot.statusFarm}} </td>
				</ng-container>

				<!-- Position  Status-->
				<ng-container matColumnDef="areaLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Area</th>
					<td mat-cell *matCellDef="let lot"> {{lot.areaLot}} </td>
				</ng-container>

				<!-- Position  Status-->
				<ng-container matColumnDef="heighLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Height</th>
					<td mat-cell *matCellDef="let lot"> {{lot.heighLot}} </td>
				</ng-container>

				<!-- Position  Status-->
				<ng-container matColumnDef="priceLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Price</th>
					<td mat-cell *matCellDef="let lot"> {{lot.priceLot}} </td>
				</ng-container>

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
	  			<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" (click)="selection.toggle(row)"></tr>
			</table>

			<mat-paginator  [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>
		</div>
		`
})


// <div class="sort sort-up" [ngClass]="{'selected': tableService.sort === column.propertyKey}" (click)="sortAsc(column.propertyKey)"></div>

export class LotListComponent implements OnInit {
	confirmDelete = true;
	// Define  order column name
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatPaginator) paginator: MatPaginator;

	columnsToDisplay = ['select', 'id', 'nameLot', 'farm.nameFarm', 'statusLot', 'areaLot', 'heighLot', 'priceLot'];
	pageSizeOptions: number[] = [5, 10, 25];
	selection = new SelectionModel<Lot>(true, []);
	lots: Lot[];

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
	) {}

	ngOnInit() {
		// this.dataSource.sort = this.sort;
		this.lotService.getAll().subscribe(data => {
			console.log(data);
			this.lots = data['result'];
			console.log(this.lots);
		});
		//this.lots.find.paginator = this.paginator;
		// console.log(this.dataSource);
	}

	test() {
		console.log('asd');
	}

	/** Whether the number of selected elements matches the total number of rows. */
	isAllSelected() {
		const numSelected = this.selection.selected.length;
		const numRows = this.lots.length;
		return numSelected === numRows;
	}

	/** Selects all rows if they are not all selected; otherwise clear selection. */
	masterToggle() {
		this.isAllSelected() ?
			this.selection.clear() :
			this.lots.forEach(row => this.selection.select(row));
	}

	setPageSizeOptions(setPageSizeOptionsInput: string) {
		this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
	}
}
