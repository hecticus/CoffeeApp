import { Component, OnInit } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';

@Component({
	selector: 'app-harvest-create',
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
			`,
	styleUrls: ['./invoice.component.css']
})

export class HarvestCreateComponent implements OnInit {

	operacion: Operacion [] = [
		{ id: 1, name: 'Nueva Cosecha' },
		{ id: 2, name: 'Nueva Compra' }
	];

	constructor() { }

	ngOnInit() {
	}

}
