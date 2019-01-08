import { InvoiceDetail } from '../../core/models/invoice-detail';
import { InvoiceDetailService } from './invoice-detail.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, ViewChild, Input, Output } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatFooterRowDef } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { Invoice } from '../../core/models/invoice';
import { BaseService } from '../../core/base.service';

@Component({
	selector: 'app-invoice-detail-read',
	styleUrls: ['./invoice-detail.component.css'],
	template: `
		<div class="mat-elevation-z8" >
			<!-- Definition table -->
			<table  mat-table [dataSource]="invoices" class="table mat-elevation-z8">

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
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Tipo de Iten</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.itemType?.nameItemType || '-'}}</td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position lot.nameLot -->
				<ng-container matColumnDef="lot.nameLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Nombre del Lote</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.lot?.nameLot || '-'}} </td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position FechaInvoiceDetail-->
				<ng-container matColumnDef="startDate">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Fecha de Apertura</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{ invoiceDetail.startDate || '-'}}</td>
					<td mat-footer-cell *matFooterCellDef><strong>Total </strong></td>
				</ng-container>

				<!-- Position store.nameStore -->
				<ng-container matColumnDef="store.nameStore">
					<th class="table-header" mat-header-cell *matHeaderCellDef><span>Nombre de la Tienda</span></th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.store?.nameStore|| '-'}} </td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position  priceItemTypeByLot -->
				<ng-container matColumnDef="priceItemTypeByLot">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Precio</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.priceItemTypeByLot|| '-'}} </td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position costItemType -->
				<ng-container matColumnDef="costItemType">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Costo</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.costItemType|| '-'}}</td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position amountInvoiceDetail -->
				<ng-container matColumnDef="amountInvoiceDetail">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Cantidad</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.amountInvoiceDetail|| '-'}}</td>
					<td mat-footer-cell *matFooterCellDef> {{ getTotalCantidad()|| '-'}} </td>
				</ng-container>

				<!-- Position nameReceived -->
				<ng-container matColumnDef="nameReceived">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Recibido</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.nameReceived|| '-'}}</td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position nameReceived-->
				<ng-container matColumnDef="nameDelivered">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Entregado</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.nameDelivered|| '-'}}</td>
					<td mat-footer-cell *matFooterCellDef></td>
				</ng-container>

				<!-- Position nameDelivered-->
				<ng-container matColumnDef="totalInvoiceDetail">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Total</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.totalInvoiceDetail|| '-' }}</td>
					<td mat-footer-cell *matFooterCellDef> {{ total || '-' }}</td>
				</ng-container>

				<!-- Position statusInvoiceDetail
				<ng-container matColumnDef="statusInvoiceDetail">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Name Delivered</th>
					<td mat-cell *matCellDef="let invoiceDetail"> {{invoiceDetail.statusInvoiceDetail || '-'}}</td>
				</ng-container>-->

				<tr mat-header-row *matHeaderRowDef="columnsToDisplay"></tr>
				<tr mat-row *matRowDef="let row; columns: columnsToDisplay;" class="element-row"  (click)="read(row.id)"></tr>
				<tr mat-footer-row *matFooterRowDef="columnsToDisplay; sticky: true"></tr>

			</table>
			<!--<mat-paginator [pageSizeOptions]="pageSizeOptions" showFirstLastButtons></mat-paginator>-->
		</div>

	`
})

export class InvoiceDetailListComponent implements OnInit {

	// @Input() idInvoice: number;
	// @Input() total: number;
	// @Output() invoiceDetail: InvoiceDetail;
	invoice: InvoiceDetail;

	form: FormGroup;
	totall: number;
	// Order Columns Display
	columnsToDisplay = ['itemType.nameItemType',
	'lot.nameLot', 'store.nameStore', 'startDate', 'priceItemTypeByLot',
	'costItemType', 'amountInvoiceDetail', 'nameReceived',
	'nameDelivered', 'totalInvoiceDetail' ];

	// 'invoice.provider.nameProvider'
	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [ 10, 20];

	invoices = new MatTableDataSource<InvoiceDetail>();

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
		this.invoices.sort = this.sort;
		this.invoices.paginator = this.paginator;

		this.activatedRoute.params.subscribe(params => {
			this.invoice = params['invoiceId'];
		});
		
		let hhtpParams = BaseService.jsonToHttpParams({
			invoice: this.invoice
		});
		


		this.invoiceDetailService.getAll(hhtpParams).subscribe(
			data => {
				this.invoices.data = data['result'];
				console.log(' --------------------hkgjgkjgjgjjjjjjjjjg---------------------- ');
				console.log(this.invoices);
		});

		// this.totall = this.total;
	}

	getTotalCantidad() {
		return this.invoices.data.map(t => t.amountInvoiceDetail).reduce((acc, value) => acc + value, 0);
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
		const numRows = this.invoices.data.length;
		return numSelected === numRows;
		}

	/** Selects all rows if they are not all selected; otherwise clear selection. */
	masterToggle() {
	this.isAllSelected() ?
		this.selection.clear() :
		this.invoices.data.forEach(row => this.selection.select(row));
	}

	applyFilter(filterValue: string) {
		this.invoices.filter = filterValue.trim().toLowerCase();
	}

	read(id: number) {
		this.router.navigate(['./invoicesDetails/' + id], {relativeTo: this.activatedRoute});
		console.log(this.activatedRoute);

	}
}
