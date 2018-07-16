import { Status } from './../../core/models/status';
import { StatusLotService } from './../status/status-lot.service';
import { Farm } from '../../core/models/farm';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';

import { LotService } from './lot.service';
import { Lot } from '../../core/models/lot';
import { FarmService } from '../farm/farm.service';

@Component({
	styleUrls: ['./lot.component.css'],
	template: `
		<h2 class="title">Create Lot</h2>
		<form [formGroup]="form" (ngSubmit)="create()">
			<fieldset>
				<legend><span>Lot data</span></legend>

				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControlName]="'farm'">
								<mat-option>-- None --</mat-option>
								<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
							</mat-select>
							<mat-label><b>Name Farm</b></mat-label>
						</mat-form-field>
						<app-validator  [control]="form.controls['farm']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="nameLot" placeholder="Name Lot">
						</mat-form-field>
						<app-validator  [control]="form.controls['nameLot']"></app-validator>
					</div>
				</div>

				<table class="example-full-width" cellspacing="0">
					<tr>
						<td>
							<mat-form-field class="example-full-width">
							  <input matInput formControlName="areaLot" placeholder="Area">
							  <app-validator [control]="form.controls['areaLot']"></app-validator>
							</mat-form-field></td>
						<td>
							<mat-form-field class="example-full-width">
							  <input matInput formControlName="heighLot" placeholder="Heich">
							  <app-validator [control]="form.controls['heighLot']"></app-validator>
							</mat-form-field>
						</td>
						<td>
							<mat-form-field class="example-full-width">
								<input matInput formControlName="priceLot" placeholder="Price" type="number" class="example-right-align">
								<span matPrefix>$&nbsp;</span>
								<!--<span matSuffix>.00</span>-->
							</mat-form-field>
							<app-validator  [control]="form.controls['priceLot']"></app-validator>
						</td>
					</tr>
				</table>

				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControlName]="'status'">
								<mat-option>-- None --</mat-option>
								<mat-option *ngFor="let s of status" [value]="{id: f.id}">{{s.name}}</mat-option>
							</mat-select>
							<mat-label>Status</mat-label>
						</mat-form-field>
					</div>
				</div>
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text">Save</button>
			</div>
		</form>
  `
})


export class LotCreateComponent implements OnInit  {
	lot: Lot;
	form: FormGroup;
	farms: Farm[];
	options: FormGroup;
	status: Status;

	foods = [
		{id: 1, value: 'steak-0', viewValue: 'Steak'},
		{id: 2, value: 'pizza-1', viewValue: 'Pizza'},
		{id: 3, value: 'tacos-2', viewValue: 'Tacos'}
	];

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private lotService: LotService,
		private farService: FarmService,
		private statusLotService: StatusLotService,
	) {
		this.form = this.lotService.getLot(new Lot());
	}

	ngOnInit () {
		this.farService.getAll().subscribe(
			data => {
				this.farms = data['result'];
				console.log(this.farms);
			});

		this.statusLotService.getAll().subscribe(
			data => {
				this.status = data['result'];
				console.log(this.status);
				console.log("holalala");
			});
	}

	create() {
		this.lotService.create(<Lot> this.form.value);
		// .subscribe(store => {
			// this.notificationService.sucessInsert(store.name);
			// this.location.back();
			console.log(this.form.value);
		// });, err => this.notificationService.error(err));
	}
}
