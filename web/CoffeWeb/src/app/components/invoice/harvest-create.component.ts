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
import { FormGroup, FormArray } from '@angular/forms';
import { Invoice } from 'src/app/core/models/invoice';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';

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


				<!--<h3>new</h3> <button (click)="this.invoiceService.addHarvest()">Add Alias</button>
					<div class="wrap-fields">
					<div class="field form-field">
						<mat-form-field class="example-full-width">
							<input matInput formControlName="amountInvoiceDetail" placeholder="Cantidad" class="example-right-align">
						</mat-form-field>
						<app-validator [control]="form.controls['amountInvoiceDetail']"></app-validator>
					</div>
					</div>
					<mat-option *ngIf="it." [value]="{id: it.id}">{{it.nameItemType}}</mat-option>
					-->


				<div formArrayName="itemTypes">
					<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let item of  itemTypesForms.controls;
						let i=index" [formGroupName]="i">

						<div class="wrap-fields">
							<div class="field form-field">
								<mat-form-field class="example-full-width">
									<mat-select required [formControl]="item.controls['itemType']">
										<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
									</mat-select>
									<mat-label><b>Granja</b></mat-label>
								</mat-form-field>
							</div>
						</div>

						<div class="wrap-fields">
							<div class="field form-field">
								<mat-form-field class="example-full-width">
									<mat-select required [formControl]="item.controls['itemType']">
										<mat-option *ngFor="let it of itemType" [value]="{id: it.id}">{{it.nameItemType}}</mat-option>

									</mat-select>
									<mat-label><b>Grano</b></mat-label>
								</mat-form-field>
								<app-validator [control]="item.controls['itemType']"></app-validator>
							</div>
						</div>

						<div class="wrap-fields">
							<div class="field form-field">
								<mat-form-field class="example-full-width">
									<input matInput required formControlName="amountInvoiceDetail" placeholder="Cantidad" class="example-right-align">
								</mat-form-field>
								<app-validator [control]="item.controls['amountInvoiceDetail']"></app-validator>
							</div>
						</div>

						<div class="wrap-fields">
							<div class="field">
								<mat-form-field class="example-full-width">
									<input matInput formControlName="noteInvoiceDetail" placeholder="Observaciones">
								</mat-form-field>
								<app-validator [control]="item.controls['noteInvoiceDetail']"></app-validator>
							</div>
						</div>

						<button (click)="deleteItemType(i)">Delete</button>

					</div>

					<button (click)="addItemType()">Add Phone Number</button>

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
	auxFarm = 0;

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

		// let httpParamsItem = BaseService.jsonToHttpParams({
		// 	collection: 'id, providerType(id), nameItemType',
		// 	'providerType': 2
		// });

		this.itemTypeService.getAll().subscribe(
				data => {
					this.itemType = data['result'];
					console.log(this.itemType);
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

	get itemTypesForms() {
		return this.form.get('itemTypes') as FormArray;
	}

	deleteItemType(i) {
		this.itemTypesForms.removeAt(i);
	}

	addItemType() {
		this.itemTypesForms.push(this.invoiceService.initItemHarvest(new InvoiceDetail));
	}


}
