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
		<table mat-table [dataSource] = "lots" >
			<!-- Position Namme -->
			<ng-container matColumnDef="nameLot">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Name </th>
				<td mat-cell *matCellDef="let lot"> {{lot.nameLot}} </td>
			</ng-container>

			<!-- Position Id -->
			<ng-container matColumnDef="id">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Id </th>
				<td mat-cell *matCellDef="let lot"> {{lot.id}} </td>
			</ng-container>

			<!-- Position Farm -->
			<ng-container matColumnDef="farm.nameFarm">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Farm </th>
				<td mat-cell *matCellDef="let lot"> {{lot.farm.nameFarm}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="statusLot">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Status </th>
				<td mat-cell *matCellDef="let lot"> {{lot.statusFarm}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="areaLot">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Area </th>
				<td mat-cell *matCellDef="let lot"> {{lot.areaLot}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="heighLot">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Height </th>
				<td mat-cell *matCellDef="let lot"> {{lot.heighLot}} </td>
			</ng-container>

			<!-- Position  Status-->
			<ng-container matColumnDef="priceLot">
				<th mat-header-cell *matHeaderCellDef mat-sort-header> Price </th>
				<td mat-cell *matCellDef="let lot"> {{lot.priceLot}} </td>
			</ng-container>

			<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
			<tr mat-row *matRowDef="let myRowData; columns: columnsToDisplay"></tr>
		</table>
		`
})




export class LotListComponent implements OnInit {
	confirmDelete = true;
	columnsToDisplay = ['id', 'nameLot', 'farm.nameFarm', 'statusLot', 'areaLot', 'heighLot', 'priceLot'];

	// tslint:disable-next-line:member-ordering
	lots = [ {
		"id": 1,
		"deleted": false,
		"createdAt": "2018-06-26 18:06:25",
		"updatedAt": "2018-06-26 18:06:25",
		"farm": {
			"id": 1,
			"deleted": false,
			"createdAt": "2018-06-26 18:06:25",
			"updatedAt": "2018-06-26 18:06:25",
			"nameFarm": "granja 1",
			"statusFarm": null
		},
		"nameLot": "LOTE 1",
		"areaLot": "89lk",
		"heighLot": 895,
		"priceLot": 89,
		"statusLot": null,
		"price_lot": 89
	},
	{
		"id": 2,
		"deleted": false,
		"createdAt": "2018-06-26 18:06:25",
		"updatedAt": "2018-06-26 18:06:25",
		"farm": {
			"id": 2,
			"deleted": true,
			"createdAt": null,
			"updatedAt": null,
			"nameFarm": null,
			"statusFarm": null
		},
		"nameLot": "LOTE 2",
		"areaLot": "256252",
		"heighLot": 1233252,
		"priceLot": 2252,
		"statusLot": null,
		"price_lot": 2252
	}
	];

	dataSource = new MatTableDataSource(this.lots);
	@ViewChild(MatSort) sort: MatSort;
	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
	) {}

	ngOnInit() {
		this.dataSource.sort = this.sort;
	}

}
