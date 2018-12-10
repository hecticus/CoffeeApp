import { StoreService } from './../store/store.service';
import { Store } from './../../core/models/store';
import { Component, OnInit } from '@angular/core';
import { NotificationService } from './../../core/utils/notification/notification.service';
import { ItemTypeService } from './../item-type/item-type.service';
import { Lot } from './../../core/models/lot';
import { LotService } from './../lot/lot.service';
import { InvoiceService } from './invoice.service';
import { ItemType } from './../../core/models/item-type';
import { Farm } from './../../core/models/farm';
import { FarmService } from './../farm/farm.service';
import { Location } from '@angular/common';
import { Operacion } from 'src/app/core/models/Operacion';
import { ProviderService } from '../provider/provider.service';
import { FilterService } from 'src/app/core/utils/filter/filter.service';
import { BaseService } from 'src/app/core/base.service';
import { FormGroup, FormArray } from '@angular/forms';
import { Invoice } from 'src/app/core/models/invoice';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { Provider } from '@angular/compiler/src/core';

@Component({
	selector: 'app-purchase-create',
	template: `
	<h2 class="title">Coffe</h2>
			`,
	styleUrls: ['./invoice.component.css']
})

export class PurchaseCreateComponent implements OnInit {

	cosecheros: Provider[];
	stores: Store[];
	form: FormGroup;
	itemType: ItemType[];
	lots: Lot[];
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
	) { }

	ngOnInit() {
		this.begins();
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


	begins() {
		let httpParams = BaseService.jsonToHttpParams({
			collection: 'id, nameProvider, nitProvider',
			'providerType': 1,
			'statusProvider': 41
		});

		this.providerService.getAll(httpParams).subscribe(
			data => {
				this.cosecheros = data['result'];
			}
		);

		let httpParamsStore = BaseService.jsonToHttpParams({
			collection: 'id, nameFarm',
		});

		this.storeService.getAll().subscribe(
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

	}
}
