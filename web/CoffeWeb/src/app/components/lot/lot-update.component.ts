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
							<mat-select required>
								<mat-option>-- None --</mat-option>
								<mat-option value="foods" *ngFor="let f of foods ">{{f.viewValue}}</mat-option>
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
export class LotUpdateComponent implements OnInit {
	form: FormGroup;
	confirmDelete = true;
	farms: Farm[];
	lot = new Lot();

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private location: Location,
		private farmService: FarmService,
	) {
		this.form = this.lotService.getLot(new Lot());
	}

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
				// this.lotService.getById(params['lotId']).subscribe(
				//	data => { this.lot = data['result'];
				// console.log(this.lot); }
				// );
				console.log(params);
				console.log('estoy en update');
			});

			this.farmService.getAll().subscribe( data => {
			this.farms = data['result']; }
		);

	}

	update(this) {
		console.log(this.farm);
	}

}
