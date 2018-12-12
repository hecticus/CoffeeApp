import { Purities } from './../../core/models/purities';
import { PurityService } from './../purity/purity.service';
import { StoreService } from './../store/store.service';
import { Store } from './../../core/models/store';
import { Component, OnInit } from '@angular/core';
import { NotificationService } from './../../core/utils/notification/notification.service';
import { ItemTypeService } from './../item-type/item-type.service';
import { Lot } from './../../core/models/lot';
import { LotService } from './../lot/lot.service';
import { InvoiceService } from './invoice.service';
import { ItemType } from './../../core/models/item-type';
import { Location } from '@angular/common';
import { ProviderService } from '../provider/provider.service';
import { FilterService } from 'src/app/core/utils/filter/filter.service';
import { BaseService } from 'src/app/core/base.service';
import { FormGroup, FormArray } from '@angular/forms';
import { Invoice } from 'src/app/core/models/invoice';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { Provider } from '@angular/compiler/src/core';

@Component({
	selector: 'app-purchase-create',
	styleUrls: ['./invoice.component.css'],
	template: `
	<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
		<fieldset>
			<legend><span>Datos de la Factura</span></legend>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="example-full-width">
						<mat-select required [formControl]="form.controls['provider']">
							<mat-option *ngFor="let p of providers" [value]="{id: p.id}">{{p.nameProvider}}</mat-option>
						</mat-select>
						<mat-label><b>Proveedor</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['provider']"></app-validator>
				</div>
			</div>

			<div formArrayName="itemtypes">
				<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let item of itemTypesForms.controls;
					let i=index" [formGroupName]="i">

					<div class="wrap-fields">
						<div class="field form-field">
							<mat-form-field class="example-full-width">
								<mat-select required [formControl]="item.controls['store']">
									<mat-option *ngFor="let s of stores" [value]="{id: s.id}">{{s.nameStore}}</mat-option>
								</mat-select>
								<mat-label><b>Acopio</b></mat-label>
							</mat-form-field>
							<app-validator [control]="item.controls['store']"></app-validator>
						</div>
					</div>

					<div class="wrap-fields">
						<div class="field form-field">
							<mat-form-field class="example-full-width">
								<mat-select required [formControl]="item.controls['itemType']">
									<mat-option *ngFor="let it of itemType" [value]="{id: it.id}">{{it.nameItemType}}</mat-option>
								</mat-select>
								<mat-label><b>Tipo</b></mat-label>
							</mat-form-field>
							<app-validator [control]="item.controls['itemType']"></app-validator>
						</div>
					</div>

					<div class="wrap-fields">
						<div class="field form-field">
							<mat-form-field class="example-full-width">
								<input matInput required formControlName="price" placeholder="Precio" class="example-right-align">
							</mat-form-field>
							<app-validator [control]="item.controls['price']"></app-validator>
						</div>
					</div>

					<div class="wrap-fields">
						<div class="field form-field">
							<mat-form-field class="example-full-width">
								<input matInput required formControlName="amountInvoiceDetail" placeholder="Peso" class="example-right-align">
							</mat-form-field>
							<app-validator [control]="item.controls['amountInvoiceDetail']"></app-validator>
						</div>
					</div>

					<div formArrayName="purities">
						<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let p of item.get('purities').controls;
							let j=index" [formGroupName]="j">

							<div class="wrap-fields">
								<div class="field form-field">
									<mat-form-field class="example-full-width">
										<mat-select required [formControl]="p.controls['idPurity']">
											<mat-option *ngFor="let p of purits" [value]="p.id">{{p.namePurity}}</mat-option>
										</mat-select>
										<mat-label><b>Grano</b></mat-label>
									</mat-form-field>
									<app-validator [control]="p.controls['idPurity']"></app-validator>
								</div>
							</div>

							<div class="wrap-fields">
								<div class="field form-field">
									<mat-form-field class="example-full-width">
										<input matInput required formControlName="valueRateInvoiceDetailPurity" placeholder="Porcentaje" class="example-right-align">
									</mat-form-field>
									<app-validator [control]="p.controls['valueRateInvoiceDetailPurity']"></app-validator>
								</div>
							</div>

							<button (click)="deleteItemType(j)">Delete</button>
						</div>
						<button (click)="addItemType()">Añadir</button>
					</div>

					<div class="wrap-fields">
						<div class="field">
							<mat-form-field class="example-full-width">
								<input matInput formControlName="nameReceived" placeholder="Recibido por">
							</mat-form-field>
							<app-validator [control]="item.controls['nameReceived']"></app-validator>
						</div>
					</div>

					<div class="wrap-fields">
						<div class="field">
							<mat-form-field class="example-full-width">
								<input matInput formControlName="nameDelivered" placeholder="Entregado por">
							</mat-form-field>
							<app-validator [control]="item.controls['nameDelivered']"></app-validator>
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

					<button (click)="addItemType()">Añadir</button>

				</div>

			<!-- -->

		</fieldset>

		<div class="options row">
			<button mat-raised-button class="btn-text" type="submit" >Guardar</button>
		</div>
	</form>
	`

})

export class PurchaseCreateComponent implements OnInit {

	providers: Provider[];
	stores: Store[];
	form: FormGroup;
	itemType: ItemType[];
	purits: Purities[];
	auxFarm = 0;

	constructor(
		private providerService: ProviderService,
		private storeService: StoreService,
		private invoiceService: InvoiceService,
		public 	filterService: FilterService,
		public 	itemTypeService: ItemTypeService,
		private lotService: LotService,
		private location: Location,
		private notificationService: NotificationService,
		private purityService: PurityService,
	) { }

	ngOnInit() {
		this.begins();
		this.form = this.invoiceService.getPurchaseCreate(new Invoice());
	}

	get itemTypesForms() {
		return this.form.get('itemtypes') as FormArray;
	}

	deleteItemType(i) {
		this.itemTypesForms.removeAt(i);
	}

	addItemType() {
		this.itemTypesForms.push(this.invoiceService.initItemPurchase(new InvoiceDetail));
	}

	// get puritiesForms() {
	// 	return this.form.controls.get('purities') as FormArray;
	// }

	deletePurities(i) {
		this.itemTypesForms.removeAt(i);
	}

	addPurities() {
		this.itemTypesForms.push(this.invoiceService.initItemPurchase(new InvoiceDetail));
	}

	create() {
		console.log(this.form);
		this.invoiceService.newHarvestPurchase(<Invoice> this.form.value)
			.subscribe(invoices => {
				this.notificationService.sucessInsert('Invoice');
				this.location.back();
			}, err =>  {
				this.notificationService.error(err);
		});
	}


	begins() {
		let httpParams = BaseService.jsonToHttpParams({
			collection: 'id, nameProvider, nitProvider',
			'providerType': 1,
			'statusProvider': 41
		});

		this.providerService.getAll(httpParams).subscribe(
			data => {
				this.providers = data['result'];
			}
		);

		let httpParamsStore = BaseService.jsonToHttpParams({
			collection: 'id, nameStore',
		});

		this.storeService.getAll(httpParamsStore).subscribe(
				data => {
					this.stores = data['result'];
				}
		);

		let httpParamsItem = BaseService.jsonToHttpParams({
			collection: 'id, providerType(id), nameItemType',
			'providerType': 1
		});

		this.itemTypeService.getAll(httpParamsItem).subscribe(
				data => {
					this.itemType = data['result'];
				}
		);

		let httpParamsPurities = BaseService.jsonToHttpParams({
			collection: 'id, namePurity'
		});

		this.purityService.getAll(httpParamsPurities).subscribe(
				data => {
					this.purits = data['result'];
					console.log(this.purits);
				}
		);

	}
}
