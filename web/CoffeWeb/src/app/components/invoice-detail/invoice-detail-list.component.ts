import { InvoiceDetail } from '../../core/models/invoice-detail';
import { InvoiceDetailService } from './invoice-detail.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { Invoice } from '../../core/models/invoice';
import { BaseService } from '../../core/base.service';

@Component({
	selector: 'app-invoice-detail-read',
	styleUrls: ['./invoice-detail.component.css'],
	template: `
		<!--<h2 class="title">Reportes</h2>

		<div class="filter row">
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
				<button class="btn-icon" type="button">
				<button class="btn-icon" title="Delete" type="button" (click)="confirmDelete = false" *ngIf="tableService.getSelectedsLength() > 0">
					<i class="material-icons">delete</i>
				</button>
			</div>
		</div>
		-->

		<div class="mat-elevation-z8" >
			<!-- Definition table -->
			<table class="table" mat-table [dataSource]="dataSource" matSort class="mat-elevation-z8">

				<!-- Checkbox Colum
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
				</ng-container> -->

				<!-- Position nameItemType -->
				<ng-container matColumnDef="itemType.nameItemType">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Item Type</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.itemType?.nameItemType || '-'}}</td>
				</ng-container>

				<!-- Position lot.nameLot -->
				<ng-container matColumnDef="lot.nameLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Name Lot</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.lot?.nameLot || '-'}} </td>
					<td mat-footer-cell *matFooterCellDef> Total </td>
				</ng-container>

				<!-- Position store.nameStore -->
				<ng-container matColumnDef="store.nameStore">
					<th class="table-header" mat-header-cell *matHeaderCellDef><span>Name Store</span></th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.store?.nameStore|| '-'}} </td>
				</ng-container>

				<!-- Position  priceItemTypeByLot -->
				<ng-container matColumnDef="priceItemTypeByLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Price</th>
						<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.priceItemTypeByLot|| '-'}} </td>
				</ng-container>

				<!-- Position costItemType -->
				<ng-container matColumnDef="costItemType">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Costo Item Type</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.costItemType|| '-'}}</td>
				</ng-container>

				<!-- Position amountInvoiceDetail -->
				<ng-container matColumnDef="amountInvoiceDetail">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Cantidad</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.amountInvoiceDetail|| '-'}}</td>
				</ng-container>

				<!-- Position nameReceived -->
				<ng-container matColumnDef="nameReceived">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Name Received</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.nameReceived|| '-'}}</td>
				</ng-container>

				<!-- Position nameReceived-->
				<ng-container matColumnDef="nameDelivered">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Name Delivered</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.nameDelivered|| '-'}}</td>
					<td mat-footer-cell *matFooterCellDef> Total </td>
				</ng-container>

				<!-- Position nameDelivered-->
				<ng-container matColumnDef="totalInvoiceDetail">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Total Detail</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.totalInvoiceDetail|| '-'}}</td>
					<td mat-footer-cell *matFooterCellDef> jjj </td>
				</ng-container>

				<!-- Position nameDelivered
				<ng-container matColumnDef="note">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Note</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.note}}</td>
				</ng-container>-->

				<!-- Position statusInvoiceDetail
				<ng-container matColumnDef="statusInvoiceDetail">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Name Delivered</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.statusInvoiceDetail}}</td>
				</ng-container>-->

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
				<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" class="element-row"  (click)="read(row.id)"></tr>
				
			</table>
			<mat-paginator [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>
		</div>

	`
})

export class InvoiceDetailListComponent implements OnInit {

	@Input() idInvoice: number;
	@Input() total: number;
	form: FormGroup;
	totall: number;
	// Order Columns Display
	columnsToDisplay = ['itemType.nameItemType',
	'lot.nameLot', 'store.nameStore', 'priceItemTypeByLot',
	'costItemType', 'amountInvoiceDetail', 'nameReceived',
	'nameDelivered', 'totalInvoiceDetail' ];

	// 'invoice.provider.nameProvider'
	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [5, 10, 20];

	dataSource = new MatTableDataSource<InvoiceDetail>();

	// Defione Selection
	selection = new SelectionModel<InvoiceDetail>(true, []);
	// const initialSelection = [];
	// const allowMultiSelect = true;
	// selection = new SelectionModel<Provider>(allowMultiSelect, initialSelection);

	seler = 4;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatPaginator) paginator: MatPaginator;
	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private invoiceDetailService: InvoiceDetailService,
	) { }

	ngOnInit() {
		this.dataSource.sort = this.sort;
		this.dataSource.paginator = this.paginator;

		let hhtpParams = BaseService.jsonToHttpParams({
			invoice: this.idInvoice,
		});
		console.log(this.total);
		this.invoiceDetailService.getAll(hhtpParams).subscribe(
			data => {
				this.dataSource.data = data['result'];
				console.log(this.dataSource);
		});
		this.totall = this.total;
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
