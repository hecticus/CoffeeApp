import { Farm } from './../../core/models/farm';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

import { LotService } from './lot.service';
import { Lot } from '../../core/models/lot';

@Component({
	styleUrls: ['./lot.component.css'],
	template: `
		<h2 class="title">Create Lot</h2>
		<form *ngIf="form" [formGroup]="form" (ngSubmit)="create()">
			<fieldset>
				<legend><span>Lot data</span></legend>

				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required>
								<mat-option>-- None --</mat-option>
								<mat-option value="foods" *ngFor="let f of foods ">{{f.viewValue}}</mat-option>
							</mat-select>
						<mat-label><b>Name Farm</b></mat-label>
						</mat-form-field>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
						<input matInput placeholder="Name Lot">
						</mat-form-field>
					</div>
				</div>

				<table class="example-full-width" cellspacing="0">
					<tr>
						<td>
							<mat-form-field class="example-full-width">
							  <input matInput placeholder="Area">
							</mat-form-field></td>
						<td>
							<mat-form-field class="example-full-width">
							  <input matInput placeholder="Heich">
							</mat-form-field>
						</td>
						<td>
							<mat-form-field class="example-full-width">
								<input matInput placeholder="Amount" type="number" class="example-right-align">
								<span matPrefix>$&nbsp;</span>
								<!--<span matSuffix>.00</span>-->
							</mat-form-field>
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


export class LotCreateComponent implements OnInit  {
	lot: Lot;
	form: FormGroup;
	farm: Farm;
	options: FormGroup;
/*     constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private lotService: LotService,
	) {} */

	foods = [
		{value: 'steak-0', viewValue: 'Steak'},
		{value: 'pizza-1', viewValue: 'Pizza'},
		{value: 'tacos-2', viewValue: 'Tacos'}
	];
	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		fb: FormBuilder) {
	/*     this.options = fb.group({
		  hideRequired: false,
		  floatLabel: 'auto',
		}); */
	}

	ngOnInit() {
	}

	create() {
		console.log('verde');
		/* this.lotService.create(<Lot> this.form.value).subscribe */
	}

	//   create() {
	// 	this.storeService.create(<Store> this.form.value).subscribe(store => {
	// 		this.notificationService.sucessInsert(store.name);
	// 		this.location.back();
	// 	}, err => this.notificationService.error(err));
	// }
}
