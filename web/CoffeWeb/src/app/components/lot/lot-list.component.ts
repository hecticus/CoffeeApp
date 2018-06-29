import { Farm } from './../../core/models/farm';
import { Hero } from './../heroes/hero';
import { TablaService } from './../../core/table/tabla.service';
import { LotService } from './lot.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { BaseService } from '../../core/base.service';
import { Lot } from '../../core/models/lot';
import { Router, ActivatedRoute } from '@angular/router';
import { FilterService } from '../../core/filter/filter.service';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
@Component({
	template: `
		<!-- Definition table -->
		<table class="table" mat-table [dataSource]="lots">
			<!-- Position Namme -->
			<ng-container matColumnDef="nameLot">
				<th class="table-header" mat-header-cell *matHeaderCellDef><span (click)="test()">Name</span></th>
				<td mat-cell *matCellDef="let lot"> {{lot.nameLot}} </td>
			</ng-container>

			<!-- Position Id -->
			<ng-container matColumnDef="id">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header> Id </th>
				<td mat-cell *matCellDef="let lot"> {{lot.id}} </td>
			</ng-container>

			<!-- Position Farm -->
			<ng-container matColumnDef="farm.nameFarm">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header> Farm </th>
				<td mat-cell *matCellDef="let lot"> {{lot.farm.nameFarm}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="statusLot">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header> Status </th>
				<td mat-cell *matCellDef="let lot"> {{lot.statusFarm}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="areaLot">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header> Area </th>
				<td mat-cell *matCellDef="let lot"> {{lot.areaLot}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="heighLot">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header> Height </th>
				<td mat-cell *matCellDef="let lot"> {{lot.heighLot}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="priceLot">
				<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header> Price </th>
				<td mat-cell *matCellDef="let lot"> {{lot.priceLot}} </td>
			</ng-container>

			<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
			<tr mat-row *matRowDef="let myRowData; columns: columnsToDisplay"></tr>
		</table>
		`,
		styleUrls: ['./lot.component.css']
})




export class LotListComponent implements OnInit {
	confirmDelete = true;
	columnsToDisplay = ['id', 'nameLot', 'farm.nameFarm', 'statusLot', 'areaLot', 'heighLot', 'priceLot'];



	// tslint:disable-next-line:member-ordering
	lots: Lot[];

	dataSource = new MatTableDataSource(this.lots);
	@ViewChild(MatSort) sort: MatSort;
	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
	) {}

	ngOnInit() {
		this.dataSource.sort = this.sort;

		this.lotService.getAll().subscribe(data => {
			console.log(data);
			this.lots = data['result'];
		});
	}

	test() {
		console.log('asd');
	}


}
