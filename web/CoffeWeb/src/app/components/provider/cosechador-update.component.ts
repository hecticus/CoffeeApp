import { NotificationService } from '../../core/utils/notification/notification.service';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';

@Component({
	selector: 'app-cosechador-update',
	styleUrls: ['./provider.component.css'],
	template: `
	<form *ngIf="form" [formGroup]="form" (ngSubmit)="update()">
		<fieldset>
			<legend><span>Datos del Cosechador</span></legend>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['deleted']">
							<mat-option [value]="true">Inactivo</mat-option>
							<mat-option [value]="false">Activo</mat-option>
						</mat-select>
						<mat-label><b>Status</b></mat-label>
					</mat-form-field>
				</div>
			</div><!-- -->
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field  required class="example-full-width">
						<input matInput formControlName="nameProvider" placeholder="Nombre">
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
						<input matInput formControlName="numberProvider" placeholder="Numero Telefónico">
						<app-validator [control]="form.controls['numberProvider']"></app-validator>
					</mat-form-field>
				</div>
				<div class="field">
					<mat-form-field class="example-full-width">
						<input matInput formControlName="emailProvider" placeholder="Correo Electrónico">
					</mat-form-field>
					<app-validator [control]="form.controls['emailProvider']"></app-validator>
				</div>
			</div>
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field  required class="example-full-width">
						<input matInput formControlName="addressProvider" placeholder="Dirección">
					</mat-form-field>
					<app-validator  [control]="form.controls['addressProvider']"></app-validator>
				</div>
			</div>
		</fieldset>

		<div class="options row">
			<button mat-raised-button class="btn-text">Guardar</button>
		</div>
	</form>
`
})


export class CosechadorUpdateComponent implements OnInit  {
	form: FormGroup;
	provider: Provider;

	constructor(
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private providerService: ProviderService,
		private notificationService: NotificationService,
	) {}

	ngOnInit () {
		this.activatedRoute.parent
			.params
			.subscribe(param => {
				this.providerService.getById(param['providerId']).subscribe(data => {
					this.provider = data['result'];
					console.log(this.provider);
					this.form = this.providerService.getCosechador(data['result']);
				});
		});
	}

	update() {
		this.form.controls['providerType'].patchValue({id: 2 });
		this.form.controls['statusProvider'].patchValue({id: 41 });
		console.log(this.form.value);
		console.log('antes de update');
		this.providerService.update(<Provider> this.form.value)
			.subscribe(provider => {
				this.notificationService.sucessUpdate('Cosechador');
				this.location.back();
				console.log(this.form.value);
			}, err => this.notificationService.error(err));
	}

}
