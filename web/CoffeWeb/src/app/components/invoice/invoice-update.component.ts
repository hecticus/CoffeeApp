import { FormGroup, FormBuilder } from '@angular/forms';
import { BaseService } from './../../core/base.service';
import { StatusInvoiceService } from './../status/status-invoice.service';
import { ProviderService } from './../provider/provider.service';
import { InvoiceService } from './invoice.service';
import { Component, OnInit, Provider } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { NotificationService } from 'src/app/core/utils/notification/notification.service';
import { Invoice } from 'src/app/core/models/invoice';
import { Status } from 'src/app/core/models/status';
import { Location } from '@angular/common';

@Component({
	styleUrls: ['./harvest.component.css'],
	template:  `
	<div class= "container">
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="update()">
		<legend><span>Datos de la Factura</span></legend>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="full-width">
						<mat-select required [formControl]="form.controls['provider']">
							<mat-option *ngFor="let c of providers" [value]="c.id">{{c.nameProvider}}</mat-option>
						</mat-select>
						<mat-label><b>Proveedor</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['provider']"></app-validator>
				</div>
			</div>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="full-width">
						<mat-select required [formControl]="form.controls['statusInvoice']">
							<mat-option *ngFor="let s of status" [value]="s.id">{{s.name}}
							</mat-option>
						</mat-select>
						<mat-label><b>Estatus</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['statusInvoice']"></app-validator>
				</div>
			</div>
			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" [disabled]="form?.invalid" >Guardar</button>
			</div>
		</form>
	</div>

	`
})
export class InvoiceUpdateComponent implements OnInit {
	invoice: Invoice;
	status: Status[];
	form: FormGroup;
	providers: Provider[];

	constructor(
		private invoiceService: InvoiceService,
		private providerService: ProviderService,
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private modalService: BsModalService,
		private notificationService: NotificationService,
		private statusInvoiceService: StatusInvoiceService,
		private fb: FormBuilder,
		private location: Location,
	) { }

	ngOnInit() {
		let idInvoice = this.activatedRoute.snapshot.parent.params.invoiceId;
		console.log(idInvoice);

		this.invoiceService.getById(idInvoice).subscribe( inv => {
			this.invoice = inv['result'];
			console.log(this.invoice);
			this.begin(this.invoice);
		});
	}

	begin( invoice: Invoice) {
		this.form = this.invoiceService.getUpdate(invoice);
		let proType = invoice.provider.providerType.id;

		let httpParamsProv =  BaseService.jsonToHttpParams({
			providerType: proType
		});

		this.providerService.getAll(httpParamsProv).subscribe( data => {
			this.providers = data['result'];
		});

		this.statusInvoiceService.getAll().subscribe( data => {
			this.status = data['result'];
		});

	}

	update() {
		console.log(this.form.value);
		this.form.controls['provider'].patchValue({id: this.form.value['provider']});
		this.form.controls['statusInvoice'].patchValue({id: this.form.value['statusInvoice']});

		this.invoiceService.update(<Invoice> this.form.value)
			.subscribe(inv => {
				this.notificationService.sucessUpdate('Item');
				this.location.back();
				console.log(this.form.value);
			}, err =>  {
				this.notificationService.error(err);
			});
	}


}
