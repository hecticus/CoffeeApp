import { Invoice } from './../../core/models/invoice';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { InvoiceService } from './invoice.service';
import { Location } from '@angular/common';
import { Status } from '../../core/models/status';
import { StatusInvoiceService } from '../status/status-invoice.service';

@Component({
	styleUrls: ['./invoice.component.css'],
	template: `
		<h3 class="title">Detalles del Reporte</h3>
		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" title="Update" type="button" (click)="update()">
					<i class="material-icons">edit</i>
				</button>
				<button class="btn-icon" title="Delete" type="button" (click)="confirmDelete = false">
					<i class="material-icons">delete</i>
				</button>
			</div>
		</div>

		<div class="answer">
			<div class="fieldset">
				<div class="legend">Datos de la Factura</div>

				<div class="wrap-fields">
					<div>
						<span class="label">Nombre del Proveedor</span>
						<span class="output">{{ invoice.provider?.nameProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Tipo de Proveedor</span>
						<span class="output">{{ invoice.provider.providerType?.nameProviderType || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Status de la Factura</span>
						<span class="output">{{ invoice.statusInvoice?.name || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Fecha de Creación</span>
						<span class="output">{{ invoice.createdAt || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Total de la Factura</span>
						<span class="output">{{ invoice.totalInvoice || '-'}}</span>
					</div>
				</div>
			</div>

			<div class="fieldset">
				<div class="legend">Detalles de la Factura</div>
				<app-invoice-detail-read   [idInvoice]="idInvoice" [total]= "invoice.totalInvoice"></app-invoice-detail-read>
			</div>
		</div>
<!--
		<app-modal [(closed)]="confirmDelete">
			<ng-template modalContentDirective>
				<div class="dialog-content">
					<div class="dialog-title" >Confirmation</div>
					<div class="dialog-message">Are you sure you want to delete this record?</div>
					<div class="dialog-options">
						<button class="btn-text red" type="button" (click)="confirmDelete = true">
							<div class="text">No</div>
						</button>
						<button class="btn-text green" type="button" (click)="delete(); confirmDelete = true">
							<div class="text">Yes</div>
						</button>
					</div>
				</div>
			</ng-template>
		</app-modal>-->

	`
})

export class InvoiceReadComponent implements OnInit {
	confirmDelete = true;
	status: Status;
	invoice = new Invoice();
	idInvoice: number;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private invoiceService: InvoiceService,
		private statusInvoiceService: StatusInvoiceService,
		private location: Location,

	) { }

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
			this.idInvoice = params['invoiceId'];
		});

		this.invoiceService.getById(this.idInvoice).subscribe(
			data => { this.invoice = data['result'];
			console.log(this.idInvoice); }
		);
		// this.idInvoice = this.invoice.id;
		console.log(this.invoice);
	}

	update() {
		console.log(this.activatedRoute);
		this.router.navigate(['./update'], {relativeTo: this.activatedRoute});
	}

	delete(this) {
		this.lotService.delete(this.lot.id).subscribe( any => {
			let url = this.location.path();
			this.router.navigate([url.substr(0, url.lastIndexOf('/'))]);
			} // }, err => this.notificationService.error(err));
		);
	}
}

