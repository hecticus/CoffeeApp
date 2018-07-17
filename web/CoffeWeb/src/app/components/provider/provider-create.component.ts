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
		<h2 class="title">Create Provider</h2>
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
			<fieldset>
				<legend><span>Provider data</span></legend>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="nameProvider" placeholder="Name">
						</mat-form-field>
						<app-validator  [control]="form.controls['nameProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="nitProvider" placeholder="Provider Code">
						</mat-form-field>
						<app-validator  [control]="form.controls['nitProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControl]="form.controls['providerType']">
								<mat-option *ngFor="let f of provType" [value]="f.id">{{f.nameProviderType}}</mat-option>
							</mat-select>
							<mat-label><b>Provider Type</b></mat-label>
						</mat-form-field>
						<app-validator [control]="form.controls['providerType']"></app-validator>
					</div>
				</div>
				<!-- -->
				<div class="wrap-fields">
						<div class="field form-field">
							<mat-form-field class="example-full-width">
								<mat-select required [formControl]="form.controls['statusProvider']">
									<mat-option *ngFor="let f of status" [value]="f.id">{{f.name}}</mat-option>
								</mat-select>
								<mat-label><b>Status</b></mat-label>
							</mat-form-field>
							<app-validator [control]="form.controls['statusProvider']"></app-validator>
						</div>
				</div>
			</fieldset>
			<fieldset>
				<legend><span>Contact data</span></legend>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="contactNameProvider" placeholder="Contact Name">
						</mat-form-field>
						<app-validator  [control]="form.controls['contactNameProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="addressProvider" placeholder="Address">
						</mat-form-field>
						<app-validator  [control]="form.controls['addressProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="numberProvider" placeholder="Telephono Number">
						</mat-form-field>
					</div>
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="emailProvider" placeholder="Email">
						</mat-form-field>
						<app-validator [control]="form.controls['emailProvider']"></app-validator>
					</div>
				</div>
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" [disabled]="!form.valid">Save</button>
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

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private providerTypeService: ProviderTypeService,
		private location: Location,
		private providerService: ProviderService,
		private statusProviderService: StatusProviderService,
	) {
		// this.form = this.providerService.getFormGroupProvider(new Provider());
	}

	ngOnInit () {
		this.form = this.providerService.getFormGroupProvider(new Provider());
		// console.log(this.form);
		this.providerTypeService.getAll().subscribe(
			data => {
				this.provType = data['result'];
			}
		);

		// console.log(this.provType);
		this.statusProviderService.getAll().subscribe(
			data => {this.status = data['result'];
			console.log(this.status);
		});
		// console.log(this.status);


	}


	create() {
		console.log('this.form.value');
		// this.providerService.create(<Provider> this.form.value);
		// .subscribe(store => {
		// 	this.notificationService.sucessInsert(store.name);
		// 	this.location.back();
			console.log(this.form.value);
		// });, err => this.notificationService.error(err));
	}
}
