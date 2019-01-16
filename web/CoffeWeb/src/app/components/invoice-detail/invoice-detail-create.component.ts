import { InvoiceService } from './../invoice/invoice.service';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { InvoiceDetailService } from './invoice-detail.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { LotService } from '../lot/lot.service';
import { ItemTypeService } from '../item-type/item-type.service';
import { StoreService } from '../store/store.service';
import { FarmService } from '../farm/farm.service';
import { PurityService } from '../purity/purity.service';
import { startTimeRange } from '@angular/core/src/profile/wtf_impl';
import { Invoice } from 'src/app/core/models/invoice';
import { BaseService } from 'src/app/core/base.service';
import { Store } from 'src/app/core/models/store';
import { ItemType } from 'src/app/core/models/item-type';
import { Purities } from 'src/app/core/models/purities';
import { Farm } from 'src/app/core/models/farm';
import { Lot } from 'src/app/core/models/lot';

@Component({
	selector: 'app-invoice-detail-create',
	styleUrls: ['./invoice-detail.component.css'],
	template:  `
	<div class= "container">
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
			<legend><span>Datos de la Factura</span></legend>

			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="full-width">
						<mat-select required [formControl]="form.controls['provider']">
							<mat-option *ngFor="let p of providers" [value]="{id: p.id}">{{p.nameProvider}}</mat-option>
						</mat-select>
						<mat-label><b>Proveedor</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['provider']"></app-validator>
				</div>
				<button class="buttonStyle" (click)="addItemType()" title="Añadir Detalle a la Cosecha">
					<i class="material-icons">add_shopping_cart</i>
				</button>
			</div>

			<div formArrayName="itemtypes">
				<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let item of form.get('itemtypes').controls;
				let i=index">
					<fieldset>
						<legend><h3>Detalle{{i+1}}: </h3></legend>
						<div [formGroupName]="i">
							<div class="wrap-fields">
								<div class="field">
									<mat-form-field>
										<mat-select required [formControl]="item.controls['store']">
											<mat-option *ngFor="let s of stores" [value]="{id: s.id}">{{s.nameStore}}</mat-option>
										</mat-select>
										<mat-label><b>Acopio</b></mat-label>
									</mat-form-field>
									<app-validator [control]="item.controls['store']"></app-validator>
								</div>
								<div class="field">
									<mat-form-field>
										<mat-select required [formControl]="item.controls['itemType']">
											<mat-option *ngFor="let it of itemType" [value]="{id: it.id}">{{it.nameItemType}}</mat-option>
										</mat-select>
										<mat-label><b>Tipo</b></mat-label>
									</mat-form-field>
									<app-validator [control]="item.controls['itemType']"></app-validator>
								</div>

								<button class="buttonStyle2" (click)="deleteItemType(i)" title="Eliminar Detalle a la Cosecha">
									<i class="material-icons">delete_sweep</i>
								</button>
							</div>

							<div class="wrap-fields">
								<div class="field">
									<mat-form-field class="example-full-width">
										<input matInput required formControlName="price" placeholder="Precio" class="example-right-align">
									</mat-form-field>
									<app-validator [control]="item.controls['price']"></app-validator>
								</div>
								<div class="field form-field">
									<mat-form-field class="example-full-width">
										<input matInput required formControlName="amountInvoiceDetail" placeholder="Peso" class="example-right-align">
									</mat-form-field>
									<app-validator [control]="item.controls['amountInvoiceDetail']"></app-validator>
								</div>
								<button class="buttonStyle" (click)="addPurities(item.controls.purities)" title="Añadir Pureza a la Cosecha">
									<i class="material-icons">add_shopping_cart</i>
								</button>
							</div>

							<div formArrayName="purities">
								<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let p of item.get('purities').controls;
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
															<input matInput required formControlName="valueRateInvoiceDetailPurity" placeholder="Porcentaje" class="example-right-align">
														</mat-form-field>
														<app-validator [control]="p.controls['valueRateInvoiceDetailPurity']"></app-validator>
													</div>
													<button class="buttonStyle2" (click)="deletePurities(item.controls.purities, j)" title="Eliminar Detalle a la Cosecha">
														<i class="material-icons">delete_sweep</i>
													</button>
												</div>
											</div>
									</fieldset>
								</div>
							</div>

							<div class="wrap-fields">
								<div class="field">
									<mat-form-field class="example-full-width">
										<input matInput formControlName="nameReceived" placeholder="Recibido por">
									</mat-form-field>
									<app-validator [control]="item.controls['nameReceived']"></app-validator>
								</div>

								<div class="field">
									<mat-form-field>
										<input matInput formControlName="nameDelivered" placeholder="Entregado por">
									</mat-form-field>
									<app-validator [control]="item.controls['nameDelivered']"></app-validator>
								</div>
							</div>

							<div class="wrap-fields">
								<div class="field">
									<mat-form-field class="full-width">
										<input matInput formControlName="noteInvoiceDetail" placeholder="Observaciones">
									</mat-form-field>
									<app-validator [control]="item.controls['noteInvoiceDetail']"></app-validator>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>

			<!-- -->

			<div class="options row">
				<button mat-raised-button class="btn-text" type="submit" >Guardar</button>
			</div>
		</form>
	</div>



	<div class= "container">
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
		<legend><span>Datos de la Factura</span></legend>
			<div class="wrap-fields">
				<div class="field form-field">
					<mat-form-field class="full-width">
						<mat-select required [formControl]="form.controls['provider']">
							<mat-option *ngFor="let c of cosecheros" [value]="{id: c.id}">{{c.nameProvider}}</mat-option>
						</mat-select>
						<mat-label><b>Cosechador</b></mat-label>
					</mat-form-field>
					<app-validator [control]="form.controls['provider']"></app-validator>
				</div>
				<button class="buttonStyle" (click)="addItemType()" title="Añadir Detalle a la Cosecha">
					<i class="material-icons">add_shopping_cart</i>
				</button>
			</div>
			<div formArrayName="itemtypes">
				<div style="margin-top:5px; margin-bottom:5px;" *ngFor="let item of form.get('itemtypes').controls;
					let i=index">
					<fieldset>
						<legend><h4>Detalle{{i+1}}: </h4></legend>
						<div [formGroupName]="i">
							<div class="wrap-fields">
								<div class="field">
									<mat-form-field>
										<mat-select required>
											<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
										</mat-select>
										<mat-label><b>Granja</b></mat-label>
									</mat-form-field>
								</div>
								<div class="field">
									<mat-form-field>
										<mat-select required [formControl]="item.controls['lot']">
											<mat-option *ngFor="let l of lots" [value]="{id: l.id}">{{l.nameLot}}</mat-option>
										</mat-select>
										<mat-label><b>Lote</b></mat-label>
									</mat-form-field>
									<app-validator [control]="item.controls['lot']"></app-validator>
								</div>
								<div class="field">
									<mat-form-field>
										<mat-select required [formControl]="item.controls['itemType']">
											<mat-option *ngFor="let it of itemType" [value]="{id: it.id}">{{it.nameItemType}}</mat-option>
										</mat-select>
										<mat-label><b>Grano</b></mat-label>
									</mat-form-field>
									<app-validator [control]="item.controls['itemType']"></app-validator>
								</div>
								<button class="buttonStyle2" (click)="deleteItemType(i)" title="Eliminar Detalle a la Cosecha">
									<i class="material-icons">delete_sweep</i>
								</button>
							</div>
							<div class="wrap-fields">
								<div class="field">
									<mat-form-field class="full-width2">
										<input matInput required formControlName="amountInvoiceDetail" placeholder="Cantidad"
										class="example-right-align">
									</mat-form-field>
									<app-validator [control]="item.controls['amountInvoiceDetail']"></app-validator>
								</div>
								<div class="field">
									<mat-form-field class="full-width">
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
				<button mat-raised-button class="btn-text" type="submit" [disabled]="form?.invalid" >Guardar</button>
			</div>
		</form>
	</div>
	`
})
export class InvoiceDetailCreateComponent implements OnInit {

	idInvoice: number;
	form: FormGroup;
	invoice: Invoice;
	operation: boolean;
	stores: Store[];
	itemType: ItemType[];
	purities: Purities[];
	farms: Farm[];
	lots: Lot[];
	invoiceDetail = new InvoiceDetail();

	constructor(
		private activatedRoute: ActivatedRoute,
		private invoiceDetailService: InvoiceDetailService,
		private lotService: LotService,
		private itemTypeService: ItemTypeService,
		private storeService: StoreService,
		private farmService: FarmService,
		private purityService: PurityService,
		private invoiceService: InvoiceService,
	) { }

	ngOnInit() {
		this.idInvoice = this.activatedRoute.snapshot.parent.params.invoiceId;
		this.form = this.invoiceDetailService.initInvoiceDetail(this.invoiceDetail);

		this.begins(1);
	}

	begins(provType: number) {
		// 	"id": 1, "Vendedor",	"id": 2, "Cosechador"
		// let provType =  this.invoiceDetail.invoice.provider.providerType.id;
		if ( provType === 1) {
			this.operation = false;

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
				console.log(this.purities);
			});
		} else {
			this.operation = true;

			let httpParamsFarm = BaseService.jsonToHttpParams({
				collection: 'id, nameFarm',
			});

			this.farmService.getAll(httpParamsFarm).subscribe( data => {
						this.farms = data['result'];
			});

			let httpParamsLots = BaseService.jsonToHttpParams({
				collection: 'id, farm(id), nameLot'
			});

			this.lotService.getAll(httpParamsLots).subscribe(
					data => {
						this.lots = data['result'];
					}
			);
		}

		let httpParamsItem = BaseService.jsonToHttpParams({
			collection: 'id, providerType(id), nameItemType',
			'providerType': provType
		});

		this.itemTypeService.getAll(httpParamsItem).subscribe(
				data => {
					this.itemType = data['result'];
				}
		);
	}
}
