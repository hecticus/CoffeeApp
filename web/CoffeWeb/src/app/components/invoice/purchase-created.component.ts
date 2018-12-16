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
	styleUrls: ['./purchase.component.css'],
	templateUrl: './pp.html'
	//  `
	// `

})

export class PurchaseCreateComponent implements OnInit {

	providers: Provider[];
	stores: Store[];
	form: FormGroup;
	itemType: ItemType[];
	purits: Purities[];
	data: Invoice[];
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

	addItemType() {
		let control = <FormArray>this.form.controls.itemtypes;
		control.push(this.invoiceService.initItemPurchase(new InvoiceDetail));
	}

	deleteItemType(i) {
		let control = <FormArray>this.form.controls.itemtypes;
		control.removeAt(i);
	}

	addPurities() {
		this.invoiceService.initPurities(new Purities);
	}

	deletePurities(control, i) {
		control.removeAt(i);
	}

	// setItems() {
	// 	let control = <FormArray>this.form.controls.itemtypes;
	// 	this.data.itemtypes.forEach(x => {
	// 	  control.push(this.fb.group({ 
	// 		company: x.company, 
	// 		projects: this.setProjects(x) }))
	// 	})
	//   }
	
	//   setProjects(x) {
	// 	let arr = new FormArray([])
	// 	x.projects.forEach(y => {
	// 	  arr.push(this.fb.group({ 
	// 		projectName: y.projectName 
	// 	  }))
	// 	})
	// 	return arr;
	//   }


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
