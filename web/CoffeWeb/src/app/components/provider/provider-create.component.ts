import { ToastrManager } from 'ng6-toastr-notifications';
import { StatusProviderService } from '../status/status-provider.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Farm } from '../../core/models/farm';
import { FarmService } from '../farm/farm.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';
import { ProviderType } from '../../core/models/provider-type';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { Status } from '../../core/models/status';

@Component({
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Crear Proveedor</h2>
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
			<fieldset>
				<legend><span>Datos del Proveedor</span></legend>
				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControl]="form.controls['providerType']">
								<mat-option  *ngFor="let f of provType" [value]="f.id" >
									{{f.nameProviderType}}
								</mat-option>
							</mat-select>
							<mat-label><b>Tipo de Proveedor</b></mat-label>
						</mat-form-field>
						<app-validator [control]="form.controls['providerType']"></app-validator>
					</div>
				</div>
			</fieldset>

			<ng-container *ngIf="form.value['providerType'] == 1; then vendedor else cosechador">
			</ng-container>
				<!-- </div>*ngIf = "f.nameProviderType  == 'vendedor'; then proveedor; else cosechador" -->
				<ng-template #vendedor>
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
								<mat-form-field  required class="example-full-width">
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
				</ng-template>

				<ng-template #cosechador>
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
							</div>
						</div>
					</fieldset>
				</ng-template>
			
			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" [disabled]="!form.valid">Guardar</button>
			</div>

		</form>
  `
})


export class ProviderCreateComponent implements OnInit  {
	lot: Lot;
	form: FormGroup;
	provType: ProviderType[];
	options: FormGroup;
	status: Status[];
	value: number;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private providerTypeService: ProviderTypeService,
		private location: Location,
		private providerService: ProviderService,
		private statusProviderService: StatusProviderService,
		private toastr: ToastrManager,
	) {}

	ngOnInit () {
		this.form = this.providerService.getFormGroupProvider(new Provider());

		this.providerTypeService.getAll().subscribe(
			data => {
				this.provType = data['result'];
			}
		);

		this.statusProviderService.getAll().subscribe(
			data => {this.status = data['result'];
			console.log(this.status);
		});
	}

	create() {
		this.form.controls['providerType'].patchValue({id: this.form.value['providerType']});
		this.form.controls['statusProvider'].patchValue({id: this.form.value['statusProvider']});
		this.providerService.create(<Provider> this.form.value)
		.subscribe(provider => {
			this.toastr.successToastr('Success create', provider.nameProvider);
			this.location.back();
		}, err => this.toastr.errorToastr('This is error', err));
	}
}
