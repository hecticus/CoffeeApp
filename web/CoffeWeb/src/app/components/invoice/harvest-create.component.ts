import { NotificationService } from './../../core/utils/notification/notification.service';
import { ItemTypeService } from './../item-type/item-type.service';
import { Lot } from './../../core/models/lot';
import { LotService } from './../lot/lot.service';
import { InvoiceService } from './invoice.service';
import { ItemType } from './../../core/models/item-type';
import { Farm } from './../../core/models/farm';
import { FarmService } from './../farm/farm.service';
import { Location } from '@angular/common';
import { Component, OnInit, Provider } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';
import { ProviderService } from '../provider/provider.service';
import { FilterService } from 'src/app/core/utils/filter/filter.service';
import { BaseService } from 'src/app/core/base.service';
import { FormGroup, FormArray } from '@angular/forms';
import { Invoice } from 'src/app/core/models/invoice';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { StatusStoreModule } from '../status/status-store.module';

@Component({
	selector: 'app-harvest-create',
	styleUrls: ['./harvest.component.css'],
	template: `
	<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">

    <legend><span>Datos de la Factura</span></legend>
		<div class="wrap-fields">
			<div class="field form-field">
				<mat-form-field class="create-width">
					<mat-select required [formControl]="form.controls['provider']">
						<mat-option *ngFor="let c of cosecheros" [value]="{id: c.id}">{{c.nameProvider}}</mat-option>
					</mat-select>
					<mat-label><b>Cosechador</b></mat-label>
				</mat-form-field>
				<app-validator [control]="form.controls['provider']"></app-validator>
			</div>

			<button (click)="addItemType()" title="Añadir Detalle a la Cosecha">
				<i class="material-icons">add_shopping_cart</i>
			</button>
		</div>


		<!---->
		<div formArrayName="itemtypes">
			<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let item of  itemTypesForms.controls;
				let i=index">

				<fieldset>
					<legend><h3>Detalle{{i+1}}: </h3>
						<button (click)="deleteItemType(i)" title="Eliminar Detalle a la Cosecha">
								<i class="material-icons">delete_sweep</i>
						</button>
					</legend>
					<div [formGroupName]="i">

						<div class="wrap-fields">
							<div class="fieldHarvest">
								<mat-form-field>
									<mat-select required [formControl]="item.controls['itemType']">
										<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
									</mat-select>
									<mat-label><b>Granja</b></mat-label>
								</mat-form-field>
							</div>

							<div class="fieldHarvest">
								<mat-form-field>
									<mat-select required [formControl]="item.controls['lot']">
										<mat-option *ngFor="let l of lots" [value]="{id: l.id}">{{l.nameLot}}</mat-option>
									</mat-select>
									<mat-label><b>Lote</b></mat-label>
								</mat-form-field>
								<app-validator [control]="item.controls['lot']"></app-validator>
							</div>

							<div class="fieldHarvest">
								<mat-form-field>
									<mat-select required [formControl]="item.controls['itemType']">
										<mat-option *ngFor="let it of itemType" [value]="{id: it.id}">{{it.nameItemType}}</mat-option>
									</mat-select>
									<mat-label><b>Grano</b></mat-label>
								</mat-form-field>
								<app-validator [control]="item.controls['itemType']"></app-validator>
							</div>
						</div>

						<button (click)="deleteItemType(i)" title="Eliminar Detalle a la Cosecha">
							<i class="material-icons">delete_sweep</i>
						</button>

						<div class="wrap-fields">
							<div class="fieldHarvest">
								<mat-form-field>
									<input matInput required formControlName="amountInvoiceDetail" placeholder="Cantidad"
									class="example-right-align">
								</mat-form-field>
								<app-validator [control]="item.controls['amountInvoiceDetail']"></app-validator>
							</div>

							<div class="fieldHarvest">
								<mat-form-field>
									<input matInput formControlName="noteInvoiceDetail" placeholder="Observaciones">
								</mat-form-field>
								<app-validator [control]="item.controls['noteInvoiceDetail']"></app-validator>
							</div>
						</div>
					</div>

				</fieldset>
			</div>
		</div>

        <div class="options row">
            <button mat-raised-button class="btn-text" type="submit" >Guardar</button>
        </div>
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

	constructor(
		private providerService: ProviderService,
		private farmService: FarmService,
		private invoiceService: InvoiceService,
		public filterService: FilterService,
		public itemTypeService: ItemTypeService,
		private lotService: LotService,
		private location: Location,
		private notificationService: NotificationService,
	) { }

	ngOnInit() {
		this.started();
		this.form = this.invoiceService.getHarvestCreate(new Invoice());
	}

	get itemTypesForms() {
		return this.form.get('itemtypes') as FormArray;
	}

	deleteItemType(i) {
		this.itemTypesForms.removeAt(i);
	}

	addItemType() {
		this.itemTypesForms.push(this.invoiceService.initItemHarvest(new InvoiceDetail));
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

	started() {
		let httpParams = BaseService.jsonToHttpParams({
			collection: 'id, nameProvider, nitProvider',
			'providerType': 2,
			'statusProvider': 41
		});

		this.providerService.getAll(httpParams).subscribe(
			data => {
				this.cosecheros = data['result'];
				console.log(this.cosecheros);

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
	}

}
