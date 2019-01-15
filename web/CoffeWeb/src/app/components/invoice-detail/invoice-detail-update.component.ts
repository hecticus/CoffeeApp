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
import { InvoiceService } from './../invoice/invoice.service';
import { ActivatedRoute, Params } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Store } from 'src/app/core/models/store';
import { StoreService } from '../store/store.service';
import { Purities } from 'src/app/core/models/purities';
import { Invoice } from 'src/app/core/models/invoice';

@Component({
	selector: 'app-invoice-detail-update',
	// styleUrls: ['./invoice-detail-update.component.css']
	template: `
	<div class= "container">
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="update()">
			<div class="field" *ngIf="operation">
				<mat-form-field>
					<mat-select required>
						<mat-option *ngFor="let f of farms" [value]="{id: f.id}">{{f.nameFarm}}</mat-option>
					</mat-select>
					<mat-label><b>Granja</b></mat-label>
				</mat-form-field>
			</div>

			<div class="field" *ngIf="operation">
				<mat-form-field>
					<mat-select required [formControl]="form.controls['lot']">
						<mat-option *ngFor="let l of lots" [value]="{id: l.id}">{{l.nameLot}}</mat-option>
					</mat-select>
					<mat-label><b>Lote</b></mat-label>
				</mat-form-field>
				<app-validator [control]="form.controls['lot']"></app-validator>
			</div>

			<div class="field">
				<mat-form-field>
					<mat-select required [formControl]="form.controls['itemType']">
						<mat-option *ngFor="let it of itemType" [value]="{id: it.id}">{{it.nameItemType}}</mat-option>
					</mat-select>
					<mat-label><b>Grano</b></mat-label>
				</mat-form-field>
				<app-validator [control]="form.controls['itemType']"></app-validator>
			</div>

		</form>
	</div>
	`,

})

export class InvoiceDetailUpdateComponent implements OnInit {
	// 1 true  harvest    //////// 0 false buyCoffe
	operation: boolean;
	idInvoice: number;
	idDetail: number;
	invoiceDetail: InvoiceDetail;
	stores: Store[];
	itemType: ItemType[];
	purities: Purities[];
	farms: Farm[];
	form: FormGroup;

	constructor(
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private itemTypeService: ItemTypeService,
		private storeService: StoreService,
		private farmService: FarmService,
		private purityService: PurityService,
		private invoiceDetailService: InvoiceDetailService,
	) { }

	ngOnInit() {
		this.idDetail = this.activatedRoute.snapshot.parent.params.invoiceDetailId;

		this.invoiceDetailService.getById(this.idDetail).subscribe( data => {
			this.invoiceDetail = data['result'];
			this.form = this.invoiceDetailService.initInvoiceDetail( data['result']);
		});
		// console.log(this.form);
		// this.form = this.invoiceDetailService.initInvoiceDetail(this.invoiceDetail);
		this.begins();
	}

	begins() {
		// 	"id": 1, "Vendedor",	"id": 2, "Cosechador"

		let provType =  this.invoiceDetail.invoice.provider.providerType.id;
		if ( provType === 1) {
			this.operation = false;

			let httpParamsStore = BaseService.jsonToHttpParams({
				collection: 'id, nameStore',
			});

			this.storeService.getAll(httpParamsStore).subscribe(
					data => {
						this.stores = data['result'];
					}
			);

			let httpParamsPurities = BaseService.jsonToHttpParams({
				collection: 'id, namePurity'
			});

			this.purityService.getAll(httpParamsPurities).subscribe(
					data => {
						this.purities = data['result'];
						console.log(this.purities);
					}
			);
		} else {
			this.operation = true;

			let httpParamsFarm = BaseService.jsonToHttpParams({
				collection: 'id, nameFarm',
			});

			this.farmService.getAll(httpParamsFarm).subscribe(
					data => {
						this.farms = data['result'];
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

	update() {
		console.log(this.form.value);
		// this.form.controls['farm'].patchValue({id: this.form.value['farm']});
		// this.form.controls['statusLot'].patchValue({id: this.form.value['statusLot']});

		// this.lotService.update(<Lot> this.form.value)
		// 	.subscribe(lot => {
		// 		this.notificationService.sucessUpdate('Lote');
		// 		this.location.back();
		// 		console.log(this.form.value);
		// 	}, err =>  {
		// 		this.notificationService.error(err);
		// 	});
	}

}
