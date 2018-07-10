import { StatusProviderService } from '../status/status-provider.service';
import { ActivatedRoute, Router, Params } from '@angular/router';
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
		<h2 class="title">Edit Provider</h2>
		<form *ngIf="form" [formGroup]="form" (ngSubmit)="create()">
			<fieldset>
				<legend><span>Provider data</span></legend>

				<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<mat-select required [formControlName]="'providerType'" (changed)="changeProviderType($event)">
								<!--<mat-option>-- None --</mat-option>-->
								<mat-option *ngFor="let f of provType" [value]="{id: f.id}">{{f.nameProviderType}}</mat-option>
							</mat-select>
							<mat-label><b>Provider Type</b></mat-label>
						</mat-form-field>
						<app-validator [control]="form.controls['providerType']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<input matInput formControlName="nameProvider" placeholder="Provider Name">
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
							<mat-select required [formControlName]="'statusProvider'" >
								<!--<mat-option>-- None --</mat-option>-->
								<mat-option *ngFor="let s of status " [value]="{id: s.id}">{{s.name}}</mat-option>
							</mat-select>
							<mat-label>Status</mat-label>
						</mat-form-field>
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

				<table class="example-full-width" cellspacing="0">
					<tr>
						<td>
							<mat-form-field class="example-full-width">
							  <input matInput formControlName="emailProvider" placeholder="Email">
							</mat-form-field>
							<app-validator [control]="form.controls['emailProvider']"></app-validator>
						</td>
						<td>
							<mat-form-field class="example-full-width">
								<input matInput formControlName="contactNameProvider" placeholder="Contact Name" class="example-right-align">
							</mat-form-field>
							<app-validator  [control]="form.controls['contactNameProvider']"></app-validator>
						</td>
					</tr>
					<tr>
						<mat-form-field class="example-full-width">
							<input matInput formControlName="numberProvider" placeholder="Telefono Number">
							<app-validator [control]="form.controls['numberProvider']"></app-validator>
						</mat-form-field>
					</tr>
				</table>
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text">Save</button>
			</div>
		</form>
  `
})


export class ProviderUpdateComponent implements OnInit  {
	lot: Lot;
	form: FormGroup;
	provType: ProviderType[];
	options: FormGroup;
	status: Status[];
	provider: Provider;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private providerTypeService: ProviderTypeService,
		private location: Location,
		private providerService: ProviderService,
		private statusProviderService: StatusProviderService,
	) {
		// this.form = this.providerService.getProvider(new Provider());
	}

	ngOnInit () {
		// this.prov.getAll().subscribe(
		// 	data => {
		// 		this.farms = data['result'];
		// 		console.log(this.farms);
		// 	});
		this.providerTypeService.getAll().subscribe(
			data => {
				this.provType = data['result'];
			});

		this.statusProviderService.getAll().subscribe(
			data => {this.status = data['result'];
			console.log('aquinnnnnn');
			console.log(this.status);
		});

		// this.activatedRoute.parent
		// 	.params
		// 	.subscribe( param => {
		// 		this.providerService.getById(+param['providerId']).subscribe( provider => {
		// 			// this.provider = data['result'];
		// 			this.form = this.providerService.getProvider(provider);
		// 		});
		// });

		this.providerService.getById(1130).subscribe( data => {
			this.provider = data['result'];
			console.log(data);
			console.log(this.provider);
			this.form = this.providerService.getProvider(this.provider);
			console.log(this.form);
		});
	}

	create() {
		console.log(this.provider);
		// this.lotService.create(<Lot> this.form.value);
		// // .subscribe(store => {
		// 	// this.notificationService.sucessInsert(store.name);
		// 	// this.location.back();
		// 	console.log(this.form.value);
		// // });, err => this.notificationService.error(err));
	}

	changeProviderType(providerType: ProviderType) {
		this.form.controls.providerType.patchValue(providerType);
	}
}
