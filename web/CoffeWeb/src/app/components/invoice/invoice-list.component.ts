import { ExcelService } from './../../core/utils/excel/excel.service';
import { MY_DATE_FORMATS } from './../../core/utils/custom-date-adapter.component';
import { NotificationService } from './../../core/utils/notification/notification.service';
import { ProviderService } from './../provider/provider.service';
import { Status } from './../../core/models/status';
import { BaseService } from '../../core/base.service';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { InvoiceService } from './invoice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit, Provider, ViewChild, TemplateRef } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { MatTableDataSource, MatPaginator, MatSort} from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { Invoice } from '../../core/models/invoice';
import { Pager } from '../../core/models/pager';
import { StatusInvoiceService } from 'src/app/components/status/status-invoice.service';
import { FilterService } from 'src/app/core/utils/filter/filter.service';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { CustomDateAdapter } from '../../core/utils/custom-date-adapter.component';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {IMyDpOptions, IMyDateModel} from 'mydatepicker';

import * as _moment from 'moment';

const moment =  _moment;

@Component({
	styleUrls: ['./invoice.component.css'],
	template: `
		<div class="container1">
			<div class="title">
				<h2>Reportes</h2>
			</div>

			<div class="button1">
				<div class="buttons">
					<button class="b1" (click)="openModal(template)">
						<i class="material-icons">lock</i>
					</button>
					<button (click)="exportTotalAsXLSX()">
						<i  class="material-icons">receipt</i>
					</button>
					<button (click)="exportDetailAsXLSX()">
						<i class="material-icons">description</i>
					</button>
				</div>
			</div>
			<!--<div class=""></div>
			StartDate-->
			<div class="date1">
				<my-date-picker [options]="myDatePickerOptions"
					[(ngModel)]="filterService.filter['startDate']"
					(change)="filterService.put('startDate',
					$event.target.value)">
				</my-date-picker>
			</div>

			<!--EndDate-->
			<div class="date2">
				<my-date-picker [options]="myDatePickerOptions"
					[(ngModel)]="filterService.filter['closedDate']"
					(change)="filterService.put('closedDate',
					$event.target.value)">
				</my-date-picker>
			</div>

			<div class="item5">
				<input matInput placeholder="Nombre o Identificación" [(ngModel)]="filterService.filter['nitName']"
									(change)="filterService.put('nitName',
									$event.target.value)">
			</div>

			<div class="item6">
				<mat-select placeholder="Tipo de Proveedor" [(ngModel)]="filterService.filter['typeProvider']"
							(change)="filterService.put('typeProvider',
							$event.target.value)">
					<mat-option>Ninguna</mat-option>
					<mat-option *ngFor="let pt of provType" [value]="pt.id"> {{pt.nameProviderType }} </mat-option>
				</mat-select>
			</div>

			<div class="item7">
				<mat-select placeholder="Estatus" [(ngModel)]="filterService.filter['statusInvoice']"
							(change)="filterService.put('statusInvoice',
							$event.target.value)">
					<mat-option>Ninguna</mat-option>
					<mat-option *ngFor="let s of status" [value]="s.id"> {{s.name}} </mat-option>
				</mat-select>
			</div>

			<div class="item8">
				<button (click)="list(0)">
					<i class="material-icons">search</i>
				</button>
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
				<ng-container matColumnDef="startDate">
					<th class="table-header" mat-header-cell *matHeaderCellDef  mat-sort-header>Fecha de Apertura</th>
						<td mat-cell *matCellDef="let invoice"> {{invoice.startDate || '-'}} </td>
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

		<ng-template #template>
			<div class="modal-body text-center">
				<div class="dialog-title">Confirmación </div>
				<div class="dialog-message">¿Estas seguro que quieres cerrar todas las facturas?</div>
				<div class="dialog-options">
					<button class="btn-text green" type="button" (click)="close()">
						<div class="text">Si</div>
					</button>
					<button class="btn-text red" type="button" (click)="decline()" >
						<div class="text">No</div>
					</button>
				</div>
			</div>
		</ng-template>

	`,
	providers: [
		{
			provide: DateAdapter, useClass: CustomDateAdapter
		},
		{
			provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS
		}
	]
})

export class InvoiceListComponent implements OnInit {

	modalRef: BsModalRef;
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
	minDate = new Date(2018, 0, 4);
	maxDate = new Date();

	minDate2 = new Date(2000, 0, 1);
	maxDate2 = new Date();

	// Order Columns Display
	columnsToDisplay = ['select',  'provider.providerType.nameProviderType',
	'statusInvoice.name', 'provider.nameProvider', 'startDate', 'totalInvoice'];

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

	myDatePickerOptions: IMyDpOptions = {
		// other options...
		dateFormat: 'yyyy-mm-dd',
		minYear: 2017,
		firstDayOfWeek: 'mo',
		sunHighlight: true,
	};

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
		public  filterService: FilterService,
		private notificationService: NotificationService,
		private modalService: BsModalService,
		private adapter: DateAdapter<any>,
		private excelService: ExcelService,
		private fb: FormBuilder,
	) {
	}

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
		let startDate = this.filterService.filter['startDate'];
		let closedDate = this.filterService.filter['closedDate'];

		if ( startDate === undefined || startDate === null ) {
			delete this.filterService.filter['startDate'];
		} else if (startDate['formatted'] !== undefined &&
			startDate['formatted'] !== null ) {
				console.log(this.filterService.filter['startDate']);
				console.log(startDate['formatted'] + 'T00:00:00Z');
				delete this.filterService.filter['startDate'];
				this.filterService.put('startDate', startDate['formatted'] + 'T00:00:00Z');
		}

		if ( closedDate === undefined || closedDate === null ) {
			delete this.filterService.filter['closedDate'];
		} else if (closedDate['formatted'] !== undefined &&
				closedDate['formatted'] !== null ) {
			console.log(this.filterService.filter['closedDate']);
			console.log(closedDate['formatted'] + 'T00:00:00Z');
			delete this.filterService.filter['closedDate'];
			this.filterService.put('closedDate', closedDate['formatted'] + 'T00:00:00Z');
		}

		if (this.filterService.filter['statusInvoice'] === undefined) {
			delete this.filterService.filter['statusInvoice'];
		}

		if (this.filterService.filter['typeProvider'] === undefined) {
			delete this.filterService.filter['typeProvider'];
		}

		if (this.filterService.filter['nitName'] === undefined) {
			delete this.filterService.filter['nitName'];
		}

		// console.log(moment(this.date).format('YYYY-MM-DD') + 'T00:00:00Z');
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

	close() {
		this.invoiceService.close({'ids': this.pageSizeOptions})
			.subscribe(closes => {
				this.modalRef.hide();
				this.notificationService.showSuccess();
			}, err =>  {
				this.notificationService.error(err);
				this.modalRef.hide();
			});
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
	openModal(template: TemplateRef<any>) {
		this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
	}

	decline(): void {
		this.modalRef.hide();
	}
	// change(date: Date) {
	// 	console.log("id");
	// 	this.filterService.put('nitName', moment(date).format('YYYY-MM-DD') + 'T00:00:00Z');
	// }

	exportAsXLSX(): void {

		let httpParams = BaseService.jsonToHttpParams({
			collection: 'startDate, closedDate, provider(nameProvider, nitProvider,' +
				'providerType(nameProviderType), statusProvider(name), totalInvoice',
			...this.filterService.filter
		});

		this.invoiceService.getAll(httpParams).subscribe(
				data => {
					console.log(data['result']);
					this.excelService.exportAsExcelFile(data['result'], 'Reporte');
				}
		);
	}

	exportTotalAsXLSX(): void {
		let startDate = this.filterService.filter['startDate'];
		let closedDate = this.filterService.filter['closedDate'];

		if ( startDate === undefined || startDate === null ) {
			delete this.filterService.filter['startDate'];
		} else if (startDate['formatted'] !== undefined &&
			startDate['formatted'] !== null ) {
				console.log(this.filterService.filter['startDate']);
				console.log(startDate['formatted'] + 'T00:00:00Z');
				delete this.filterService.filter['startDate'];
				this.filterService.put('startDate', startDate['formatted'] + 'T00:00:00Z');
		}

		if ( closedDate === undefined || closedDate === null ) {
			delete this.filterService.filter['closedDate'];
		} else if (closedDate['formatted'] !== undefined &&
				closedDate['formatted'] !== null ) {
			console.log(this.filterService.filter['closedDate']);
			console.log(closedDate['formatted'] + 'T00:00:00Z');
			delete this.filterService.filter['closedDate'];
			this.filterService.put('closedDate', closedDate['formatted'] + 'T00:00:00Z');
		}

		if (this.filterService.filter['statusInvoice'] === undefined) {
			delete this.filterService.filter['statusInvoice'];
		}

		if (this.filterService.filter['typeProvider'] === undefined) {
			delete this.filterService.filter['typeProvider'];
		}

		if (this.filterService.filter['nitName'] === undefined) {
			delete this.filterService.filter['nitName'];
		}

		// console.log(moment(this.date).format('YYYY-MM-DD') + 'T00:00:00Z');
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

		this.invoiceService.getTotal(httpParams).subscribe(
				data => {
					console.log(data['result']);
					this.excelService.exportAsExcelFile(data['result'], 'Reporte');
				}
		);
	}

	exportDetailAsXLSX(): void {

		let startDate = this.filterService.filter['startDate'];
		let closedDate = this.filterService.filter['closedDate'];

		if ( startDate === undefined || startDate === null ) {
			delete this.filterService.filter['startDate'];
		} else if (startDate['formatted'] !== undefined &&
			startDate['formatted'] !== null ) {
				console.log(this.filterService.filter['startDate']);
				console.log(startDate['formatted'] + 'T00:00:00Z');
				delete this.filterService.filter['startDate'];
				this.filterService.put('startDate', startDate['formatted'] + 'T00:00:00Z');
		}

		if ( closedDate === undefined || closedDate === null ) {
			delete this.filterService.filter['closedDate'];
		} else if (closedDate['formatted'] !== undefined &&
				closedDate['formatted'] !== null ) {
			console.log(this.filterService.filter['closedDate']);
			console.log(closedDate['formatted'] + 'T00:00:00Z');
			delete this.filterService.filter['closedDate'];
			this.filterService.put('closedDate', closedDate['formatted'] + 'T00:00:00Z');
		}

		if (this.filterService.filter['statusInvoice'] === undefined) {
			delete this.filterService.filter['statusInvoice'];
		}

		if (this.filterService.filter['typeProvider'] === undefined) {
			delete this.filterService.filter['typeProvider'];
		}

		if (this.filterService.filter['nitName'] === undefined) {
			delete this.filterService.filter['nitName'];
		}

		// console.log(moment(this.date).format('YYYY-MM-DD') + 'T00:00:00Z');
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

		this.invoiceService.getDetail(httpParams).subscribe(
				data => {
					console.log(data['result']);
					this.excelService.exportAsExcelFile(data['result'], 'Reporte');
				}
		);
	}

	onDateChanged(event: IMyDateModel) {
		// event properties are: event.date, event.jsdate, event.formatted and event.epoc
	}

}

