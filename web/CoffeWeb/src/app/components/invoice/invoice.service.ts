import { Purities } from './../../core/models/purities';
import { Farm } from './../../core/models/farm';
import { InvoiceDetail } from './../../core/models/invoice-detail';
import { ItemType } from './../../core/models/item-type';
import { Invoice } from '../../core/models/invoice';
import { FormBuilder, FormControl, FormGroup, Validators, FormArray } from '@angular/forms';
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

	create(invoice: Invoice): Observable<Invoice> {
		return this.http.post<any>(InvoiceService.BASE_URL, invoice);
	}

	newHarvestPurchase(invoice: Invoice): Observable<Invoice> {
		return this.http.post<any>(InvoiceService.BASE_URL + '2', invoice);
	}

	update(invoice: Invoice): Observable<Invoice> {
		return this.http.put<any>(InvoiceService.BASE_URL + '/' + invoice.id, invoice);
	}

	close(ids: {'ids': number[]}): Observable<Invoice> {
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

	addPurchase() {
		// add detail to the list
		const control = <FormArray>this.fb.control['itemTypes'];
		control.push(this.initItemHarvest(new InvoiceDetail()));
	}

	removePurchase(i: number) {
		// remove detail from the list
		const control = <FormArray>this.fb.control['itemTypes'];
		control.removeAt(i);
	}

	getHarvestCreate(invoice: Invoice): FormGroup {
		return this.fb.group({
			provider: new FormControl(invoice.provider ? invoice.provider.id : undefined),
			buyOption: new FormControl(true),
			startDate: new FormControl(this.dateTimeIso()),
			itemtypes: this.fb.array([
				this.initItemHarvest(new InvoiceDetail()),
				])
		});
	}

	initItemHarvest(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			lot: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			noteInvoiceDetail: new FormControl(invoiceDetail.note, [ Validators.maxLength(100)]),
			nameDelivered: new FormControl(''),
			nameReceived: new FormControl(''),
			startDate: new FormControl(this.dateTimeIso()),
		});
	}

	getPurchaseCreate(invoice: Invoice): FormGroup {
		return this.fb.group({
			provider: new FormControl(invoice.provider ? invoice.provider.id : undefined),
			buyOption: new FormControl(false),
			startDate: new FormControl(this.dateTimeIso()),
			itemtypes: this.fb.array([
				this.initItemPurchase(new InvoiceDetail())
			])
		});
	}

	initItemPurchase(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			store: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			price:  new FormControl(invoiceDetail.priceItemTypeByLot, [Validators.required, CustomValidators.numberRegex, CustomValidators.min(0)]),
			nameDelivered: new FormControl(invoiceDetail.nameDelivered, [Validators.required, Validators.maxLength(50)]),
			nameReceived: new FormControl(invoiceDetail.nameReceived, [Validators.required, Validators.maxLength(50)]),
			noteInvoiceDetail: new FormControl(invoiceDetail.note, [Validators.maxLength(50)]),
			startDate: new FormControl(this.dateTimeIso()),
			purities: this.fb.array([
				this.initPurities(new Purities())
			])
		});
	}

	initPurities(purities: Purities): FormGroup {
		return this.fb.group({
			idPurity: new FormControl(purities.idPurity, Validators.required),
			valueRateInvoiceDetailPurity: new FormControl( purities.valueRateInvoiceDetailPurity , Validators.required),
		});
	}

	dateTimeIso() {
		let today = new Date();
		let isoDate = today.toISOString();
		let aux = isoDate.split('.');
		return aux[0] + 'Z';
	}


}
