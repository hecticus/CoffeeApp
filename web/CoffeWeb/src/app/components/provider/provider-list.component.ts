
import { Params } from '@angular/router';
import { ProviderTypeService } from './../provider-type/provider-type.service';
import { ProviderService } from './provider.service';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Provider, ViewChild } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { FilterService } from '../../core/filter/filter.service';
import { MatTableDataSource, MatPaginator } from '@angular/material';

@Component({
	selector: 'app-provider.list',
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Providers</h2>
		<div class="filter row">
			<div class="field">
				<mat-select placeholder="Provider Type" [(ngModel)]="selectedValue" name="pt">
					<mat-option>-- None --</mat-option>
					<mat-option *ngFor="let pt of provType" [value]="pt.id" >
						{{pt.nameProviderType}}
					</mat-option>
				</mat-select>
			</div>
			<div class="field">
				<input matInput placeholder="Name's Provider">
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
			<table class="table" mat-table [dataSource]="dataSource">

				<!-- Position ProviderType
				<ng-container matColumnDef="provider.providerType.nameProviderType">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Provider Type</th>
					<td mat-cell *matCellDef="let provider"> {{provider.providerType.nameProviderType}} </td>
				</ng-container> -->

				<!-- Position nitProvider -->
				<ng-container matColumnDef="nitProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Nit Provider</th>
					<td mat-cell *matCellDef="let provider"> {{provider.nitProvider}} </td>
				</ng-container>

				<!-- Position Namme -->
				<ng-container matColumnDef="nameProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef><span>Name</span></th>
					<td mat-cell *matCellDef="let provider"> {{provider.nameProvider}} </td>
				</ng-container>

				<!-- Position Address -->
				<ng-container matColumnDef="addressProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Addreess</th>
					<td mat-cell *matCellDef="let provider"> {{provider.addressProvider}} </td>
				</ng-container>

				<!-- Position numberProvider -->
				<ng-container matColumnDef="numberProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Number</th>
					<td mat-cell *matCellDef="let provider"> {{provider.numberProvider}} </td>
				</ng-container>

				<!-- Position emailProvider -->
				<ng-container matColumnDef="emailProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Email</th>
					<td mat-cell *matCellDef="let provider"> {{provider.emailProvider}} </td>
				</ng-container>

				<!-- Position contactNameProvider -->
				<ng-container matColumnDef="contactNameProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Contac Name</th>
					<td mat-cell *matCellDef="let provider"> {{provider.contactNameProvider}} </td>
				</ng-container>

				<!-- Position statusProvider -->
				<ng-container matColumnDef="statusProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Status</th>
					<td mat-cell *matCellDef="let provider"> {{provider.statusProvider}} </td>
				</ng-container>

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
	  			<tr mat-row *matRowDef="let row; columns: columnsToDisplay;"></tr>
				  <mat-paginator [pageSizeOptions]="pagesize" showFirstLastButtons></mat-paginator>
			</table>
		</div>

	`
})
export class ProviderListComponent implements OnInit {
	pagesize = [5, 10, 20];
	columnsToDisplay = [ 'nitProvider', 'nameProvider',
						'statusProvider', 'addressProvider', 'emailProvider',
						'contactNameProvider', 'numberProvider'];
	selectedValue = 'hol';
	form: FormGroup;
	provType: ProviderType[];
	providers: Provider[];
	dataSource = new MatTableDataSource();

	@ViewChild(MatPaginator) paginator: MatPaginator;
	constructor(
		private providerService: ProviderService,
		private providerTypeService: ProviderTypeService,
	) { }

	ngOnInit() {
		this.dataSource.paginator = this.paginator;
		this.providerTypeService.getAll().subscribe(
			data => { this.provType = data['result'];
			console.log(this.provType);
		});

		this.providerService.getAll().subscribe(
			data => { this.dataSource.data = data['result'];
			console.log(this.dataSource);
		});
	}

	create() {
		console.log(this.selectedValue);
	}

	manejo($event: any) {
		console.log($event);
	}
}
