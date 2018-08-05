import { Status } from '../../core/models/status';
import { StatusLotService } from '../status/status-lot.service';
import { Farm } from '../../core/models/farm';
import { FormGroup } from '@angular/forms';
import { FarmService } from '../farm/farm.service';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { LotService } from './lot.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { NotificationService } from '../../core/utils/notification/notification.service';

@Component({
	styleUrls: ['./lot.component.css'],
	template: `
		<h3 class="title">Editar Lote</h3>
		<form  *ngIf="form" [formGroup]="form" (ngSubmit)="update()">
			<fieldset>
			<legend><span>Datos del Lote</span></legend>
			<!-- <div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['deleted']">
							<mat-option [value]="true">Inactivo</mat-option>
							<mat-option [value]="false">Activo</mat-option>
						</mat-select>
						<mat-label><b>Status</b></mat-label>
					</mat-form-field>
				</div>
			</div> -->
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field required class="example-full-width">
						<input matInput formControlName="nameLot" placeholder="Nombre">
					</mat-form-field>
					<app-validator [control]="form.controls['nameLot']"></app-validator>
				</div>
			</div>
			<!--<div class="wrap-fields">
				<div class="field">
					<mat-form-field  class="example-full-width">
						<mat-radio-group required [formControl]="form.controls['deleted']">
								<mat-radio-button value="true">Inactivo</mat-radio-button>
								<mat-radio-button value="false">Activo</mat-radio-button>
						</mat-radio-group>
					</mat-form-field>
				</div>
			</div>-->
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['farm']">
							<mat-option *ngFor="let f of farms" [value]="f.id">{{f.nameFarm}}</mat-option>
						</mat-select>
						<mat-label><b>Granja</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['farm']"></app-validator>
				</div>
			</div>
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
export class LotUpdateComponent implements OnInit {
	form: FormGroup;
	confirmDelete = true;
	farms: Farm[];
	lot = new Lot();
	status: Status;

	constructor(
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private location: Location,
		private farmService: FarmService,
		private statusLotService: StatusLotService,
		private notificationService: NotificationService,
	) {	}

	ngOnInit() {
		this.activatedRoute.parent.params
			.subscribe(params => {
				this.lotService.getById(params['lotId']).subscribe(data => {
					this.form = this.lotService.getLot(data['result']);
				});
			}
		);

		this.statusLotService.getAll().subscribe(
			data => {
				this.status = data['result'];
			}
		);

		this.farmService.getAll().subscribe( data => {
			this.farms = data['result']; }
		);

	}

	update() {
		console.log(this.form.value);
		this.form.controls['farm'].patchValue({id: this.form.value['farm']});
		this.form.controls['statusLot'].patchValue({id: this.form.value['statusLot']});

		this.lotService.update(<Lot> this.form.value)
			.subscribe(lot => {
				this.notificationService.sucessUpdate('Lote');
				this.location.back();
				console.log(this.form.value);
			}, err =>  {
				this.notificationService.error(err);
			});
	}

}
