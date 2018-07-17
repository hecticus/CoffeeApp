import { ToastrManager } from 'ng6-toastr-notifications';
import { Status } from './../../core/models/status';
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

@Component({
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Edit Provider</h2>
		<form *ngIf="form" [formGroup]="form" (ngSubmit)="update()">
			<fieldset>
				<legend><span>Provider data</span></legend>
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
							<input matInput formControlName="contactNameProvider" placeholder="Contact Name" class="example-right-align">
						</mat-form-field>
						<app-validator  [control]="form.controls['contactNameProvider']"></app-validator>
					</div>
				</div>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="numberProvider" placeholder="Telefono Number">
							<app-validator [control]="form.controls['numberProvider']"></app-validator>
						</mat-form-field>
					</div>
					<div class="field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="emailProvider" placeholder="Email">
						</mat-form-field>
						<app-validator [control]="form.controls['emailProvider']"></app-validator>
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
			</fieldset>

			<div class="options row">
				<button mat-raised-button class="btn-text">Save</button>
			</div>
		</form>
	`
})


export class ProviderUpdateComponent implements OnInit  {
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
		private toastr: ToastrManager
	) {}

	ngOnInit () {
		this.activatedRoute.parent
			.params
			.subscribe(param => {
				this.providerService.getById(param['providerId']).subscribe(data => {
					this.form = this.providerService.getProvider(data['result']);
				});
		});

		this.providerTypeService.getAll().subscribe(
			data => {
				this.provType = data['result'];
			}
		);
		this.statusProviderService.getAll().subscribe(
			data => {
				this.status = data['result'];
			}
		);
	}



	update() {
		console.log(this.form.value);
		this.form.controls['providerType'].patchValue({id: this.form.value['providerType']});
		this.form.controls['statusProvider'].patchValue({id: this.form.value['statusProvider']});
		console.log(789);
		console.log(this.form.value);

		this.providerService.update(<Provider> this.form.value)
			.subscribe(provider => {
				this.toastr.successToastr('Success Update', provider.nameProvider);
				this.location.back();
				console.log(this.form.value);
			}, err => this.toastr.errorToastr('This is error', err));
	}

}
