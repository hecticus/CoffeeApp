import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseService } from '../../core/base.service';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { NotificationService } from '../../core/utils/notification/notification.service';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';
import { ProviderType } from '../../core/models/provider-type';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { Status } from '../../core/models/status';
import { StatusProviderService } from '../status/status-provider.service';


@Component({
	selector: 'app-vendedor',
	styleUrls: ['./provider.component.css'],
	template: `
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
			<fieldset>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="nameProvider" placeholder="Nombre del Vendedor">
						</mat-form-field>
						<app-validator  [control]="form.controls['nameProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="nitProvider" placeholder="RUC">
						</mat-form-field>
						<app-validator  [control]="form.controls['nitProvider']"></app-validator>
					</div>
				</div>
				</fieldset>
				<fieldset>
					<legend><span>Datos de Contacto</span></legend>
					<div class="wrap-fields">
						<div class="field">
							<mat-form-field class="example-full-width">
								<input matInput formControlName="contactNameProvider" placeholder="Nombre de Contacto">
							</mat-form-field>
							<app-validator  [control]="form.controls['contactNameProvider']"></app-validator>
						</div>
					</div>
					<div class="wrap-fields">
						<div class="field">
							<mat-form-field class="example-full-width">
								<input matInput formControlName="addressProvider" placeholder="Dirección">
							</mat-form-field>
							<app-validator  [control]="form.controls['addressProvider']"></app-validator>
						</div>
					</div>
					<div class="wrap-fields">
						<div class="field">
							<mat-form-field  required class="example-full-width">
								<input matInput formControlName="numberProvider" placeholder="Número de Telefono">
							</mat-form-field>
						</div>
						<div class="field">
							<mat-form-field class="example-full-width">
								<input matInput formControlName="emailProvider" placeholder="Correo Electrónico">
							</mat-form-field>
							<app-validator [control]="form.controls['emailProvider']"></app-validator>
						</div>
					</div>
				</fieldset>
				<div class="options row">
					<button mat-raised-button class="btn-text" type="submit" [disabled]="!form.valid">Guardar</button>
				</div>
		</form>
	`
})

export class VendedorComponent implements OnInit {
	form: FormGroup;

	constructor(
		private notificationService: NotificationService,
		private location: Location,
		private providerService: ProviderService,
	) {}

	ngOnInit () {
		this.form = this.providerService.getVendedor(new Provider());
	}

	create() {
		console.log('create');
		this.form.controls['providerType'].patchValue({id: 1} );
		this.form.controls['statusProvider'].patchValue({id: 41});
		this.providerService.create(<Provider> this.form.value)
			.subscribe(provider => {
				this.notificationService.sucessInsert(this.form.value['nameProvider']);
				this.location.back();
			}, err => this.notificationService.error(err));
	}

}
