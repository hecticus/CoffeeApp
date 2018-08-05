import { FormGroup } from '@angular/forms';
import { ToastrManager } from 'ng6-toastr-notifications';
import { Status } from '../../core/models/status';
import { StatusProviderService } from '../status/status-provider.service';
import { ActivatedRoute} from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';
import { ProviderType } from '../../core/models/provider-type';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { NotificationService } from '../../core/utils/notification/notification.service';

@Component({
	selector: 'app-vendedor-update',
	styleUrls: ['./provider.component.css'],
	template: `
	<form *ngIf="form" [formGroup]="form" (ngSubmit)="update()">
		<fieldset>
			<legend><span>Datos del Vendedor</span></legend>
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
					<mat-form-field class="example-full-width">
						<input matInput formControlName="contactNameProvider" placeholder="Nombre de Contacto" class="example-right-align">
					</mat-form-field>
					<app-validator  [control]="form.controls['contactNameProvider']"></app-validator>
				</div>
			</div>
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


export class VendedorUpdateComponent implements OnInit  {
	form: FormGroup;
	provType: ProviderType[];
	options: FormGroup;
	status: Status[];
	provider: Provider;

	constructor(
		private activatedRoute: ActivatedRoute,
		private providerTypeService: ProviderTypeService,
		private location: Location,
		private providerService: ProviderService,
		private statusProviderService: StatusProviderService,
		private notificationService: NotificationService,
	) {}

	ngOnInit () {
		this.activatedRoute.parent
			.params
			.subscribe(param => {
				this.providerService.getById(param['providerId']).subscribe(data => {
					this.form = this.providerService.getProvider(data['result']);
				});
		});

		this.providerTypeService.getAll().subscribe(
			data => {
				this.provType = data['result'];
			}
		);
		this.statusProviderService.getAll().subscribe(
			data => {
				this.status = data['result'];
			}
		);
	}

	update() {
		this.form.controls['providerType'].patchValue({id: 1 });
		this.form.controls['statusProvider'].patchValue({id: 41 });

		this.providerService.update(<Provider> this.form.value)
			.subscribe(provider => {
				this.notificationService.sucessUpdate('Vendedor');
				this.location.back();
				console.log(this.form.value);
			},  err => this.notificationService.error(err));
	}

}
