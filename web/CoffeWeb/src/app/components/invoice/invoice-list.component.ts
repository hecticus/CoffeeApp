import { ProviderService } from './../provider/provider.service';
import { Status } from './../../core/models/status';
import { BaseService } from '../../core/base.service';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { InvoiceService } from './invoice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Provider, ViewChild } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { Invoice } from '../../core/models/invoice';
import { Pager } from '../../core/models/pager';
import { StatusInvoiceService } from 'src/app/components/status/status-invoice.service';
import { FilterService } from 'src/app/core/utils/filter/filter.service';

@Component({
	styleUrls: ['./invoice.component.css'],
	template: `
		<h2 class="title">Reportes</h2>

		<div class="headerSearch">
			
			<div class="rowsm">
			<!--<div class="field filter">
					<h4 class="title">Filtrar Por Fecha</h4>
				</div>-->
				<div class="field">
					<mat-form-field  color="orange">
					<mat-label>Fecha de Inicio</mat-label>
					<input  matInput [min]="minDate1" [max]="maxDate1" [matDatepicker]="picker1">
					<mat-datepicker-toggle matSuffix [for]="picker1"></mat-datepicker-toggle>
					<mat-datepicker #picker1></mat-datepicker>
					</mat-form-field>

					<mat-form-field  color="orange">
						<mat-label>Fecha de cierre</mat-label>
						<input matInput [min]="minDate2" [max]="maxDate2" [matDatepicker]="picker2">
						<mat-datepicker-toggle matSuffix [for]="picker2"></mat-datepicker-toggle>
						<mat-datepicker #picker2 color="primary"></mat-datepicker>
					</mat-form-field>
				</div>
			</div>

			<div class="rowsm">
				<div class="field">
					<input matInput placeholder="Nombre o Identificación" [(ngModel)]="filterService.filter['nitName']"
																			(change)="filterService.put('nitName',
																			$event.target.value)">
				</div>
				<div class="field">
					<mat-select placeholder="Tipo de Proveedor" [(ngModel)]="filterService.filter['typeProvider']"
																(change)="filterService.put('typeProvider',
																$event.target.value)">
						<mat-option>Ninguna</mat-option>
						<mat-option *ngFor="let pt of provType" [value]="pt.id"> {{pt.nameProviderType }} </mat-option>
					</mat-select>
				</div>

				<div class="field">
					<mat-select placeholder="Estatus" [(ngModel)]="filterService.filter['statusInvoice']"
														(change)="filterService.put('statusInvoice',
														$event.target.value)">
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
		</div>

		<!--Table -->
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

				<!-- Position Provider -->
				<ng-container matColumnDef="provider.nameProvider">
					<div class="sort sort-up"></div>
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Nombre del Proveedor</th>
					<div class="sort sort-down"></div>
					<td mat-cell *matCellDef="let invoice"> {{invoice.provider?.nameProvider || '-'}} </td>
				</ng-container>

				<!-- Position statusInvoice -->
				<ng-container matColumnDef="statusInvoice.name">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Status</th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.statusInvoice?.name || '-'}} </td>
				</ng-container>

				<!-- Position ProviderType -->
				<ng-container matColumnDef="provider.providerType.nameProviderType">
					<th class="table-header" mat-header-cell *matHeaderCellDef  mat-sort-header><span>Tipo de Proveedor</span></th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.provider.providerType?.nameProviderType || '-'}} </td>
				</ng-container>

				<!-- Position  openDateInvoice -->
				<ng-container matColumnDef="createdAt">
					<th class="table-header" mat-header-cell *matHeaderCellDef  mat-sort-header>Fecha de Apertura</th>
						<td mat-cell *matCellDef="let invoice"> {{invoice.createdAt || '-'}} </td>
				</ng-container>

				<!-- Position totalInvoice -->
				<ng-container matColumnDef="totalInvoice">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Total Invoice</th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.totalInvoice || '-' }}</td>
				</ng-container>

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
	  			<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" class="element-row"  (click)="read(row.id)"></tr>
			</table>
			<mat-paginator [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>
		</div>

	`
})

export class InvoiceListComponent implements OnInit {
	form: FormGroup;
	provType: ProviderType[];
	providers: Provider[];
	status: Status;
	pager: Pager;
	provider: Provider;
	// let currentTime = new Date();

	// let month = currentTime.getMonth() + 1;
	// let day = currentTime.getDate();
	// let year = currentTime.getFullYear();
	minDate1 = new Date(2018, 0, 1);
	maxDate1 = new Date();

	minDate2 = new Date(2000, 0, 1);
	maxDate2 = new Date();

	// Order Columns Display
	columnsToDisplay = ['select',  'provider.providerType.nameProviderType',
	'statusInvoice.name', 'provider.nameProvider', 'createdAt', 'totalInvoice'];

	// 'invoice.provider.nameProvider'
	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [15, 30, 60];

	dataSource = new MatTableDataSource<Invoice>();

	// Defione Selection
	selection = new SelectionModel<Invoice>(true, []);
	// const initialSelection = [];
	// const allowMultiSelect = true;
	// selection = new SelectionModel<Provider>(allowMultiSelect, initialSelection);

	seler = 4;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatPaginator) paginator: MatPaginator;
	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private invoiceService: InvoiceService,
		private statusInvoiceService: StatusInvoiceService,
		private providerTypeService: ProviderTypeService,
		private providerService: ProviderService,
		public filterService: FilterService,
	) { }

	ngOnInit() {
		this.dataSource.sort = this.sort;
		this.dataSource.paginator = this.paginator;
		this.filter();
		this.list();
	}


	filter() {
		let httpParams = BaseService.jsonToHttpParams({
			collection: 'id,nameProviderType'
		});

		this.providerTypeService.getAll(httpParams).subscribe(
			data => {
				this.provType = data['result'];
		});

		this.providerService.getAll(httpParams).subscribe(
			data => {
				this.provider = data['result'];
		});

		this.statusInvoiceService.getAll().subscribe(
			data => {
				this.status = data['result'];
				console.log(this.status);
			}
		);

	}

	list(page = 0) {


		if (this.filterService.filter['statusInvoice'] === undefined) {
			delete this.filterService.filter['statusInvoice'];
		}

		if (this.filterService.filter['typeProvider'] === undefined) {
			delete this.filterService.filter['typeProvider'];
		}

		let httpParams = BaseService.jsonToHttpParams({
			// sort: this.table.sort,
			// collection: 'id, nameProvider, nitProvider, addressProvider, emailProvider, contactNameProvider, numberProvider,' +
			// 			'createdAt, providerType(id, nameProviderType), statusProvider(id, name))',
			// 'pager.index': page,
			// 'pager.size': this.table.pager.pageSize,
			// ...this.filterService.filter
			// 'providerType': this.selected,
			// 'statusProvider': this.selectedStatus,
			...this.filterService.filter
		});

		console.log('$event');
		console.log(this.filterService.filter);
		this.invoiceService.getAll(httpParams).subscribe(
			data => {
				this.dataSource.data = data['result'];
				this.pager = data['pager'];
				console.log(this.pager);
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
