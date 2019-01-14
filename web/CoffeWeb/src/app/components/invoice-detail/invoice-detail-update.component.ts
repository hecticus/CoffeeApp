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
		
	</div>
	`,
	
})
// <form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
		
// 		</form>
export class InvoiceDetailUpdateComponent implements OnInit {
	//1 true  harvest    //////// 0 false buyCoffe
	operation: boolean;
	idInvoice: number;
	idDetail: number;
	invoiceDetail: InvoiceDetail;
	stores: Store[];
	itemType: ItemType[];
	purities: Purities[];
	farms: Farm[];

	constructor(
		private activatedRoute: ActivatedRoute,
		private invoiceService: InvoiceService,
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
		});
		
		this.begins();
	}

	begins() {

		this.invoiceDetail.invoice.provider.providerType


		let httpParamsFarm = BaseService.jsonToHttpParams({
			collection: 'id, nameFarm',
		});

		this.farmService.getAll(httpParamsFarm).subscribe(
				data => {
					this.farms = data['result'];
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
					this.purities = data['result'];
					console.log(this.purities);
				}
		);

	}

}
