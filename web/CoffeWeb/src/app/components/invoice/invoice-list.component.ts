import { InvoiceService } from './invoice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Provider, ViewChild } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { Invoice } from '../../core/models/invoice';

@Component({
	selector: 'app-provider.list',
	styleUrls: ['./invoice.component.css'],
	template: `
		<h2 class="title">Reportes</h2>
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
				<button class="btn-icon" type="button"> <!--
				<button class="btn-icon" title="Delete" type="button" (click)="confirmDelete = false" *ngIf="tableService.getSelectedsLength() > 0">-->
					<i class="material-icons">delete</i>
				</button>
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

				<!-- Position Provider -->
				<ng-container matColumnDef="provider.nameProvider">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Name Provider</th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.provider.nameProvider}} </td>
				</ng-container>

				<!-- Position statusInvoice -->
				<ng-container matColumnDef="statusInvoice.name">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Status Invoice</th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.statusInvoice.name}} </td>
				</ng-container>

				<!-- Position closeDateInvoice -->
				<ng-container matColumnDef="closedDateInvoice">
					<th class="table-header" mat-header-cell *matHeaderCellDef><span>Name</span></th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.closedDateInvoice}} </td>
				</ng-container>

				<!-- Position  openDateInvoice -->
				<ng-container matColumnDef="createdAt">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Addreess</th>
						<td mat-cell *matCellDef="let invoice"> {{invoice.createdAt}} </td>
				</ng-container>

				<!-- Position totalInvoice -->
				<ng-container matColumnDef="totalInvoice">
					<th class="table-header" mat-header-cell *matHeaderCellDef mat-sort-header>Total Invoice</th>
					<td mat-cell *matCellDef="let invoice"> {{invoice.totalInvoice}}</td>
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

	// Order Columns Display
	columnsToDisplay = ['select', 'provider.nameProvider',
	'statusInvoice.name', 'createdAt',	'closedDateInvoice', 'totalInvoice'];

	// 'invoice.provider.nameProvider'
	// MatPaginator Inputs
	length = 100;
	pageSize = 10;
	pageSizeOptions: number[] = [5, 10, 20];

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
	) { }

	ngOnInit() {

		this.dataSource.sort = this.sort;
		this.dataSource.paginator = this.paginator;

		this.invoiceService.getAll().subscribe(
			data => {
				this.dataSource.data = data['result'];
				console.log(this.dataSource);
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
