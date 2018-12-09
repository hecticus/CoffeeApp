import { Component, OnInit } from '@angular/core';
import { Farm } from '../../core/models/farm';
import { FarmService } from '../farm/farm.service';
import { FormGroup } from '@angular/forms';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { LotService } from './lot.service';
import { Status } from '../../core/models/status';
import { StatusLotService } from '../status/status-lot.service';
import { NotificationService } from '../../core/utils/notification/notification.service';

@Component({
	styleUrls: ['./lot.component.css'],
	template: `
		<h2 class="title">Crear Lote</h2>
		<form *ngIf="form" [formGroup]="form" (ngSubmit)="create()">
			<fieldset>
				<legend><span>Datos del Lote</span></legend>

				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControl]="form.controls['statusLot']">
								<mat-option *ngFor="let s of status" [value]="{id: s.id}">{{s.name}}</mat-option>
							</mat-select>
							<mat-label><b>Estatus</b></mat-label>
						</mat-form-field>
						<app-validator [control]="form.controls['statusLot']"></app-validator>
					</div>
				</div>

				<div class="wrap-fields">
					<div class="field">
						<mat-form-field required class="example-full-width">
							<input matInput formControlName="nameLot" placeholder="Nombre del Lote">
						</mat-form-field>
						<app-validator [control]="form.controls['nameLot']"></app-validator>
					</div>
				</div>
				<!--<div class="wrap-fields">
					<div class="field">
						<mat-form-field required class="example-full-width">
							<input matInput formControlName="nameLot" placeholder="Name">
						</mat-form-field>
						<app-validator  [control]="form.controls['nameLot']"></app-validator>
					</div>
				</div>-->
				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControl]="form.controls['farm']">
								<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
							</mat-select>
							<mat-label><b>Granja</b></mat-label>
						</mat-form-field>
						<app-validator [control]="form.controls['farm']"></app-validator>
					</div>
				</div>
				<!-- -->
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="areaLot" placeholder="Área">
						  </mat-form-field>
						  <app-validator [control]="form.controls['areaLot']"></app-validator>
					</div>
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="heighLot" placeholder="Altura">
						</mat-form-field>
						<app-validator [control]="form.controls['heighLot']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="priceLot" placeholder="Precio" class="example-right-align">
						</mat-form-field>
						<app-validator [control]="form.controls['priceLot']"></app-validator>
					</div>
				</div>
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text">Guardar</button>
			</div>
		</form>
  `
})


export class LotCreateComponent implements OnInit  {
	lot: Lot;
	form: FormGroup;
	farms: Farm[];
	status: Status;

	constructor(
		private location: Location,
		private lotService: LotService,
		private farService: FarmService,
		private statusLotService: StatusLotService,
		private notificationService: NotificationService,
	) {	}

	ngOnInit () {

		this.form = this.lotService.getFormGroupLot(new Lot());

		this.farService.getAll().subscribe(
			data => {
				this.farms = data['result'];
			}
		);

		this.statusLotService.getAll().subscribe(
			data => {
				this.status = data['result'];
			}
		);
	}

	create() {
		console.log(this.form);
		this.lotService.create(<Lot> this.form.value)
		.subscribe(lot => {
			this.notificationService.sucessInsert('Lote');
			this.location.back();
		}, err =>  {
			this.notificationService.error(err);
		});
	}
}

