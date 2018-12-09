import { InvoiceDetail } from './../../core/models/invoice-detail';
import { ItemType } from './../../core/models/item-type';
import { Invoice } from '../../core/models/invoice';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';

import { BaseService } from '../../core/base.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { CustomValidators } from '../../core/utils/validator/custom-validator';
import { Lot } from '../../core/models/lot';

@Injectable({
	providedIn: 'root'
})
export class InvoiceService {

	private static readonly BASE_URL: string = BaseService.HOST + '/invoice';

	constructor(
		private http: HttpClient,
		private fb: FormBuilder
	) { }

	create(lot: Lot): Observable<Lot> {
		return this.http.post<any>(InvoiceService.BASE_URL, lot);
	}

	update(lot: Lot): Observable<Lot> {
		return this.http.put<any>(InvoiceService.BASE_URL + '/' + lot.id, lot);
	}

	close(ids: {'ids': number[]}): Observable<Lot> {
		return this.http.post<any>(InvoiceService.BASE_URL + '/close', ids );
	}

	delete(id: number): Observable<any> {
		return this.http.delete<any>(InvoiceService.BASE_URL + '/' + id);
	}

	deletes(ids: {'ids': number[]}): Observable<any> {
	return this.http.post(InvoiceService.BASE_URL + '/deletes', ids );
	}

	getById(id: number): Observable<Lot> {
		return this.http.get<any>(InvoiceService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceService.BASE_URL, {params: params});
	}

	getTotal(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceService.BASE_URL + '/createReport', {params: params});
	}

	getDetail(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceService.BASE_URL + '/createDetailReport', {params: params});
	}

	getPagos(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceService.BASE_URL + '/createPagos', {params: params});
	}

	getLot(invoice: Invoice): FormGroup {
		return this.fb.group({
		id: new FormControl(invoice.id),
		provider: new FormControl(invoice.provider, Validators.required),
		statusInvoice: new FormControl(invoice.statusInvoice, Validators.required),
		});
	}


	createItemHarvest(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			id: new FormControl(invoiceDetail.id),
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			lot: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			nameDelivered: new FormControl(invoiceDetail.nameDelivered, [Validators.required, Validators.maxLength(50)]),
			nameReceived: new FormControl(invoiceDetail.nameReceived, [Validators.required, Validators.maxLength(50)]),
			noteInvoiceDetail: new FormControl(invoiceDetail.note, [Validators.required, Validators.maxLength(50)]),
			priceItemTypeByLot:  new FormControl(invoiceDetail.priceItemTypeByLot, [CustomValidators.numberRegex, CustomValidators.min(0)]),
		});
	}

	getHarvestCreate(invoice: Invoice): FormGroup {
		return this.fb.group({
			id: new FormControl(invoice.id),
			provider: new FormControl(invoice.provider ? invoice.provider.id : undefined),
			buyOpttion: new FormControl(false),
			startDate: new FormControl(),
			itemTypes: this.fb.array([this.createItemHarvest(new InvoiceDetail())])
		});
	}

	createItemPurchase(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			id: new FormControl(invoiceDetail.id),
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			store: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			price:  new FormControl(invoiceDetail.priceItemTypeByLot, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			nameDelivered: new FormControl(invoiceDetail.nameDelivered, [Validators.required, Validators.maxLength(50)]),
			nameReceived: new FormControl(invoiceDetail.nameReceived, [Validators.required, Validators.maxLength(50)]),
			noteInvoiceDetail: new FormControl(invoiceDetail.note, [Validators.required, Validators.maxLength(50)]),
			priceItemTypeByLot:  new FormControl(invoiceDetail.priceItemTypeByLot, [CustomValidators.numberRegex, CustomValidators.min(0)]),
		});
	}

	getPurchaseCreate(invoice: Invoice): FormGroup {
		return this.fb.group({
			id: new FormControl(invoice.id),
			provider: new FormControl(invoice.provider ? invoice.provider.id : undefined),
			buyOpttion: new FormControl(false),
			startDate: new FormControl(),
			itemTypes: this.fb.array([this.createItemPurchase(new InvoiceDetail())])
		});
	}



}
