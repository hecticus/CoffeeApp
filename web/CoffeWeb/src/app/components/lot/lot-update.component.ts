import { Status } from './../../core/models/status';
import { StatusLotService } from './../status/status-lot.service';
import { Farm } from '../../core/models/farm';
import { FormGroup } from '@angular/forms';
import { FarmService } from '../farm/farm.service';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { LotService } from './lot.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
	template: `
		<h3 class="title">Lot Edit</h3>
		<form [formGroup]="form" (ngSubmit)="update()">
			<fieldset>
			<legend><span>Lot data</span></legend>
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field required class="example-full-width">
						<input matInput formControlName="nameLot" placeholder="Name Lot">
					</mat-form-field>
					<app-validator [control]="form.controls['nameLot']"></app-validator>
				</div>
			</div>
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field required class="example-full-width">
						<input matInput formControlName="nameLot" placeholder="Name">
					</mat-form-field>
					<app-validator  [control]="form.controls['nameLot']"></app-validator>
				</div>
			</div>
			<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControl]="form.controls['statusLot']">
							<mat-option>-- None --</mat-option>
								<mat-option *ngFor="let s of status" [value]="{id: s.id}">{{s.name}}</mat-option>
							</mat-select>
							<mat-label><b>Status</b></mat-label>
						</mat-form-field>
						<app-validator [control]="form.controls['statusLot']"></app-validator>
					</div>
			</div>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['farm']">
						<mat-option>-- None --</mat-option>
							<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
						</mat-select>
						<mat-label><b>Farm</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['farm']"></app-validator>
				</div>
			</div>
			<!-- -->
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field class="example-full-width">
						<input matInput formControlName="areaLot" placeholder="Area">
					  </mat-form-field>
					  <app-validator [control]="form.controls['areaLot']"></app-validator>
				</div>
				<div class="field">
					<mat-form-field class="example-full-width">
						<input matInput formControlName="heighLot" placeholder="Heich">
					</mat-form-field>
					<app-validator [control]="form.controls['heighLot']"></app-validator>
				</div>
			</div>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<input matInput formControlName="priceLot" placeholder="Price" class="example-right-align">
					</mat-form-field>
					<app-validator [control]="form.controls['priceLot']"></app-validator>
				</div>
			</div>
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text">Save</button>
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
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private location: Location,
		private farmService: FarmService,
		private statusLotService: StatusLotService,
	) {
		this.form = this.lotService.getLot(new Lot());
		/* this.activatedRoute.parent.params.subscribe(params => console.log(params)); */
	}

/*
	constructor(private route: ActivatedRoute) {
		this.route.parent.params.subscribe(params => console.log(params)); // Object {artistId: 12345} */

	ngOnInit() {
		this.activatedRoute.parent.params.subscribe(params => {
				this.lotService.getById(+params['lotId']).subscribe(
					data => { this.lot = data['result'];
					console.log(this.lot); }
				);
				console.log(params + 'ldsnjkdsbjklvbkjdsbjvkbdskljb');
				console.log('estoy elñlñlñlñlñlñlñlñln update');
			}
		);

		this.statusLotService.getAll().subscribe(
			data => {
				this.status = data['result'];
				console.log(this.status);
				console.log('bbb');
			}
		);

		this.farmService.getAll().subscribe( data => {
			this.farms = data['result']; }
		);

	}

	update(this) {
		console.log(this.farm);
	}

}
