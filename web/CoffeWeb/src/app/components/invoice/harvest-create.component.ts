import { ItemTypeService } from './../item-type/item-type.service';
import { Lot } from './../../core/models/lot';
import { LotService } from './../lot/lot.service';
import { InvoiceService } from './invoice.service';
import { ItemType } from './../../core/models/item-type';
import { Farm } from './../../core/models/farm';
import { FarmService } from './../farm/farm.service';
import { MatTableDataSource } from '@angular/material';
import { Component, OnInit, Provider } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';
import { ProviderService } from '../provider/provider.service';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { StatusProviderService } from '../status/status-provider.service';
import { FilterService } from 'src/app/core/utils/filter/filter.service';
import { Router, ActivatedRoute } from '@angular/router';
import { BaseService } from 'src/app/core/base.service';
import { FormGroup } from '@angular/forms';
import { Invoice } from 'src/app/core/models/invoice';

@Component({
	selector: 'app-harvest-create',
	styleUrls: ['./invoice.component.css'],
	template: `
	<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
		<fieldset>
			<legend><span>Datos de la Factura</span></legend>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['provider']">
							<mat-option *ngFor="let c of cosecheros" [value]="{id: c.id}">{{c.nameProvider}}</mat-option>
						</mat-select>
						<mat-label><b>Cosechador</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['provider']"></app-validator>
				</div>
			</div>

			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required>
							<mat-option>Ninguna</mat-option>
							<mat-option *ngFor="let f of farms" [value]="f.id"> {{f.nameFarm}} </mat-option>
						</mat-select>
						<mat-label><b>Granja</b></mat-label>
					</mat-form-field>
				</div>
			</div>

			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['lot']">
							<mat-option *ngFor="let l of lots" [value]="{id: l.id}">{{l.nameLot}}</mat-option>
						</mat-select>
						<mat-label><b>Lote</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['lot']"></app-validator>
				</div>
			</div>

			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['itemType']">
							<mat-option *ngFor="let i of itemType" [value]="{id: i.id}">{{i.nameItemType}}</mat-option>
						</mat-select>
						<mat-label><b>Cosechador</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['itemType']"></app-validator>
				</div>
			</div>

			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<input matInput formControlName="amountInvoiceDetail" placeholder="Cantidad" class="example-right-align">
					</mat-form-field>
					<app-validator [control]="form.controls['amountInvoiceDetail']"></app-validator>
				</div>
			</div>

		</fieldset>
	</form>
			`
})

export class HarvestCreateComponent implements OnInit {
	cosecheros: Provider[];
	farms: Farm[];
	form: FormGroup;
	itemType: ItemType[];
	lots: Lot[];

	operacion: Operacion [] = [
		{ id: 1, name: 'Nueva Cosecha' },
		{ id: 2, name: 'Nueva Compra' }
	];

	constructor(
		private providerService: ProviderService,
		private farmService: FarmService,
		private invoiceService: InvoiceService,
		public filterService: FilterService,
		public itemTypeService: ItemTypeService,
		private lotService: LotService,
		private router: Router,
		private activatedRoute: ActivatedRoute,
	) { }

	ngOnInit() {
		let httpParams = BaseService.jsonToHttpParams({
			collection: 'id, nameProvider, nitProvider',
			'providerType': 2,
			'statusProvider': 41
		});

		this.providerService.getAll(httpParams).subscribe(
			data => {
				this.cosecheros = data['result'];
			}
		);

		let httpParamsFarm = BaseService.jsonToHttpParams({
			collection: 'id, nameFarm',
		});

		this.farmService.getAll(httpParamsFarm).subscribe(
				data => {
					this.farms = data['result'];
				}
		);

		let httpParamsItem = BaseService.jsonToHttpParams({
			collection: 'id, providerType(id), nameItemType',
			'providerType': 2
		});

		this.itemTypeService.getAll(httpParamsItem).subscribe(
				data => {
					this.itemType = data['result'];
				}
		);


		let httpParamsLots = BaseService.jsonToHttpParams({
			collection: 'id, farm(id), nameLot'
		});

		this.lotService.getAll(httpParamsLots).subscribe(
				data => {
					this.lots = data['result'];
				}
		);

		this.form = this.invoiceService.getHarvestCreate(new Invoice());
	}

}
