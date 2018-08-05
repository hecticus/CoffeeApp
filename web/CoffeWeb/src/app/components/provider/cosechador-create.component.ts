import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Location } from '@angular/common';
import { NotificationService } from '../../core/utils/notification/notification.service';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';

@Component({
	selector: 'app-cosechador-create',
	styleUrls: ['./provider.component.css'],
	template: `
	<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
		<fieldset>
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field  required class="example-full-width">
						<input matInput formControlName="nameProvider" placeholder="Nombre del Cosechador">
					</mat-form-field>
					<app-validator  [control]="form.controls['nameProvider']"></app-validator>
				</div>
			</div>
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field  required class="example-full-width">
						<input matInput formControlName="nitProvider" placeholder="DNI">
					</mat-form-field>
					<app-validator  [control]="form.controls['nitProvider']"></app-validator>
				</div>
			</div>
			</fieldset>
			<fieldset>
				<legend><span>Datos de Contacto</span></legend>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="addressProvider" placeholder="Dirección">
						</mat-form-field>
						<app-validator  [control]="form.controls['addressProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="numberProvider" placeholder="Número de Telefono">
						</mat-form-field>
					</div>
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="emailProvider" placeholder="Correo Electrónico">
						</mat-form-field>
						<app-validator  [control]="form.controls['emailProvider']"></app-validator>
					</div>
				</div>
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" [disabled]="!form.valid">Guardar</button>
			</div>
	</form>
`
})
export class CosechadorCreateComponent implements OnInit {
	form: FormGroup;

	constructor(
		private location: Location,
		private providerService: ProviderService,
		private notificationService: NotificationService,
	) {}

	ngOnInit () {
		this.form = this.providerService.getCosechador(new Provider());
	}

	create() {
		console.log('create');
		this.form.controls['providerType'].patchValue({id: 2 });
		this.form.controls['statusProvider'].patchValue({id: this.form.value['statusProvider']});
		this.providerService.create(<Provider> this.form.value)
			.subscribe(provider => {
				this.notificationService.sucessInsert('Cosechador');
				this.location.back();
			}, err =>  {
				this.notificationService.error(err);
			});
	}

}
