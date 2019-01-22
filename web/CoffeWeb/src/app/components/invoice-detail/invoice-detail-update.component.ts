import { NotificationService } from './../../core/utils/notification/notification.service';
import { Lot } from './../../core/models/lot';
import { FormGroup } from '@angular/forms';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { Farm } from './../../core/models/farm';
import { FarmService } from './../farm/farm.service';
import { ItemTypeService } from './../item-type/item-type.service';
import { PurityService } from './../purity/purity.service';
import { BaseService } from 'src/app/core/base.service';
import { ItemType } from './../../core/models/item-type';
import { LotService } from './../lot/lot.service';
import { InvoiceDetailService } from './invoice-detail.service';
import { ActivatedRoute, Params } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Store } from 'src/app/core/models/store';
import { StoreService } from '../store/store.service';
import { Purities } from 'src/app/core/models/purities';
import { Location } from '@angular/common';

@Component({
	selector: 'app-invoice-detail-update',
	styleUrls: ['./invoice-detail.component.css'],
	template: `
	<div class= "container">

	<div class= "container" *ngIf="option == 1">
		<legend><span>Actualizar item de la Compra</span></legend>
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="update(1)">
            <div class="wrap-fields">
                <div class="field">
                    <mat-form-field>
                        <mat-select required [formControl]="form.controls['store']">
                            <mat-option *ngFor="let s of stores" [value]="s.id">{{s.nameStore}}</mat-option>
                        </mat-select>
                        <mat-label><b>Acopio</b></mat-label>
                    </mat-form-field>
                    <app-validator [control]="form.controls['store']"></app-validator>
                </div>
                <div class="field">
                    <mat-form-field>
                        <mat-select required [formControl]="form.controls['itemType']">
                            <mat-option *ngFor="let it of itemType" [value]="it.id">{{it.nameItemType}}</mat-option>
                        </mat-select>
                        <mat-label><b>Tipo</b></mat-label>
                    </mat-form-field>
                    <app-validator [control]="form.controls['itemType']"></app-validator>
                </div>

                <!--<button class="buttonStyle2" (click)="deleteItemType(i)" title="Eliminar Detalle a la Cosecha">
                    <i class="material-icons">delete_sweep</i>
                </button>-->
            </div>

            <div class="wrap-fields">
                <div class="field">
                    <mat-form-field class="example-full-width">
                        <input matInput required formControlName="costItemType" placeholder="Precio" class="example-right-align">
                    </mat-form-field>
                    <app-validator [control]="form.controls['costItemType']"></app-validator>
                </div>
                <div class="field form-field">
                    <mat-form-field class="example-full-width">
                        <input matInput required formControlName="amountInvoiceDetail" placeholder="Peso" class="example-right-align">
                    </mat-form-field>
                    <app-validator [control]="form.controls['amountInvoiceDetail']"></app-validator>
                </div>
				<!--
				<button class="buttonStyle" (click)="addPurities(form.controls.purities)" title="Añadir Pureza a la Cosecha">
                    <i class="material-icons">add_shopping_cart</i>
				</button>
				-->
            </div>

			<!--
            <div formArrayName="invoiceDetailPurity">
                <div style="margin-top:5px; margin-bottom:5px;" *ngFor="let p of item.get('invoiceDetailPurity').controls;
                let j=index" >
                    <fieldset>
                        <legend><h4> Pureza{{j+1}}: </h4></legend>
                            <div [formGroupName]="j">
                                <div class="wrap-fields">
                                    <div class="field">
                                        <mat-form-field class="full-width">
                                            <mat-select required [formControl]="p.controls['idPurity']">
                                                <mat-option *ngFor="let p of purits" [value]="p.id">{{p.namePurity}}</mat-option>
                                            </mat-select>
                                            <mat-label><b>Grano</b></mat-label>
                                        </mat-form-field>
                                        <app-validator [control]="p.controls['idPurity']"></app-validator>
                                    </div>
                                </div>

                                <div class="wrap-fields">
                                    <div class="field">
                                        <mat-form-field class="full-width">
											<input matInput required formControlName="valueRateInvoiceDetailPurity"
											placeholder="Porcentaje" class="example-right-align">
                                        </mat-form-field>
                                        <app-validator [control]="p.controls['valueRateInvoiceDetailPurity']"></app-validator>
                                    </div>
									<button class="buttonStyle2" (click)="deletePurities(item.controls.purities, j)"
									title="Eliminar Detalle a la Cosecha">
                                        <i class="material-icons">delete_sweep</i>
                                    </button>
                                </div>
                            </div>
                    </fieldset>
                </div>
            </div>-->

            <div class="wrap-fields">
                <div class="field">
                    <mat-form-field class="example-full-width">
                        <input matInput formControlName="nameReceived" placeholder="Recibido por">
                    </mat-form-field>
                    <app-validator [control]="form.controls['nameReceived']"></app-validator>
                </div>

                <div class="field">
                    <mat-form-field>
                        <input matInput formControlName="nameDelivered" placeholder="Entregado por">
                    </mat-form-field>
                    <app-validator [control]="form.controls['nameDelivered']"></app-validator>
                </div>
            </div>

            <div class="wrap-fields">
                <div class="field">
                    <mat-form-field class="full-width">
                        <input matInput formControlName="noteInvoiceDetail" placeholder="Observaciones">
                    </mat-form-field>
                    <app-validator [control]="form.controls['noteInvoiceDetail']"></app-validator>
                </div>
            </div>
			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" [disabled]="form?.invalid" >Guardar</button>
			</div>
		</form>
	</div>

	<div *ngIf="option == 2">
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="update(2)">
		<legend><span>Actualizar item de la Cosecha</span></legend>
			<div class="wrap-fields">
				<div class="field">
					<mat-form-field>
						<mat-select required [formControl]="form.controls['farm']">
							<mat-option *ngFor="let f of farms" [value]="f.id">{{f.nameFarm}}</mat-option>
						</mat-select>
						<mat-label><b>Granja</b></mat-label>
					</mat-form-field>
				</div>
				<div class="field">
					<mat-form-field>
						<mat-select required [formControl]="form.controls['lot']">
							<mat-option *ngFor="let l of lots" [value]="l.id">{{l.nameLot}}</mat-option>
						</mat-select>
						<mat-label><b>Lote</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['lot']"></app-validator>
				</div>
				<div class="field">
					<mat-form-field>
						<mat-select required [formControl]="form.controls['itemType']">
							<mat-option *ngFor="let it of itemType" [value]="it.id">{{it.nameItemType}}</mat-option>
						</mat-select>
						<mat-label><b>Grano</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['itemType']"></app-validator>
				</div>
				<!--
				<button class="buttonStyle2" (click)="deleteItemType(i)" title="Eliminar Detalle a la Cosecha">
					<i class="material-icons">delete_sweep</i>
				</button>-->
			</div>

			<div class="wrap-fields">
				<div class="field">
					<mat-form-field class="full-width2">
						<input matInput required formControlName="amountInvoiceDetail" placeholder="Cantidad"
						class="example-right-align">
					</mat-form-field>
					<app-validator [control]="form.controls['amountInvoiceDetail']"></app-validator>
				</div>
				<div class="field">
					<mat-form-field class="full-width">
						<input matInput formControlName="noteInvoiceDetail" placeholder="Observaciones">
					</mat-form-field>
					<app-validator [control]="form.controls['noteInvoiceDetail']"></app-validator>
				</div>
			</div>

			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" [disabled]="form?.invalid" >Guardar</button>
			</div>
		</form>
	</div>

	</div>
	`,
})

export class InvoiceDetailUpdateComponent implements OnInit {
	// 1 true  harvest    //////// 0 false buyCoffe
	option: number;
	idInvoice: number;
	idDetail: number;
	invoiceDetail: InvoiceDetail;
	stores: Store[];
	itemType: ItemType[];
	purities: Purities[];
	farms: Farm[];
	lots: Lot[];
	form: FormGroup;

	constructor(
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private itemTypeService: ItemTypeService,
		private storeService: StoreService,
		private farmService: FarmService,
		private purityService: PurityService,
		private invoiceDetailService: InvoiceDetailService,
		private notificationService: NotificationService,
		private location: Location,
	) { }

	ngOnInit() {
		this.idDetail = this.activatedRoute.snapshot.parent.params.invoiceDetailId;

		this.invoiceDetailService.getById(this.idDetail).subscribe( data => {
			this.invoiceDetail = data['result'];
			console.log(this.invoiceDetail);
			this.begins(this.invoiceDetail.invoice.provider.providerType.id);
		});

	}

	begins(provType: number) {
		// 	"id": 1, "Vendedor",	"id": 2, "Cosechador"
		// let provType =  this.invoiceDetail.invoice.provider.providerType.id;
		this.option = provType;

		if ( provType === 1) {
			this.form = this.invoiceDetailService.initPurchaseDetail(this.invoiceDetail);
			let httpParamsStore = BaseService.jsonToHttpParams({
				collection: 'id, nameStore',
			});

			this.storeService.getAll(httpParamsStore).subscribe( data => {
				this.stores = data['result'];
			});

			let httpParamsPurities = BaseService.jsonToHttpParams({
				collection: 'id, namePurity'
			});

			this.purityService.getAll(httpParamsPurities).subscribe( data => {
				this.purities = data['result'];
			});
		} else {
			console.log(this.invoiceDetail);
			this.form = this.invoiceDetailService.initHarvestDetail(this.invoiceDetail);
			console.log(this.form);

			let httpParamsFarm = BaseService.jsonToHttpParams({
				collection: 'id, nameFarm',
			});

			this.farmService.getAll(httpParamsFarm).subscribe( data => {
					this.farms = data['result'];
			});

			let httpParamsLots = BaseService.jsonToHttpParams({
				collection: 'id, farm(id), nameLot'
			});

			this.lotService.getAll(httpParamsLots).subscribe( data => {
				this.lots = data['result'];
			});
		}

		let httpParamsItem = BaseService.jsonToHttpParams({
			collection: 'id, providerType(id), nameItemType',
			'providerType': provType
		});

		this.itemTypeService.getAll(httpParamsItem).subscribe( data => {
					this.itemType = data['result'];
		});
	}

	update(id: number) {
		console.log(this.form.value);
		this.form.controls['invoice'].patchValue({id: this.form.value['invoice']});
		if (id === 1) {
			this.form.controls['store'].patchValue({id: this.form.value['store']});
			this.form.controls['itemType'].patchValue({id: this.form.value['itemType']});
			this.form.controls['invoiceDetailPurity'].patchValue(this.invoiceDetail.invoiceDetailPurity);
		} else {
			this.form.controls['lot'].patchValue({id: this.form.value['lot']});
			this.form.controls['itemType'].patchValue({id: this.form.value['itemType']});
		}

		this.invoiceDetailService.update(<InvoiceDetail> this.form.value)
			.subscribe(invDetail => {
				this.notificationService.sucessUpdate('Item');
				this.location.back();
				console.log(this.form.value);
			}, err =>  {
				this.notificationService.error(err);
			});
	}

}
