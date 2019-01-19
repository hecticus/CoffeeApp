import { Params } from '@angular/router';
import { BaseService } from '../../core/base.service';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { CustomValidators } from 'src/app/core/utils/validator/custom-validator';
import { Purities } from 'src/app/core/models/purities';
import { InvoiceDetailPurity } from 'src/app/core/models/invoice-detail-purity';

@Injectable({
	providedIn: 'root'
})

export class InvoiceDetailService {

	private static readonly BASE_URL: string = BaseService.HOST + '/invoiceDetail';

	constructor(
		private http: HttpClient,
		private fb: FormBuilder,
	) { }

	update(invoiceDetail: InvoiceDetail) {
		return this.http.put<any>(InvoiceDetailService.BASE_URL + '/' + invoiceDetail.id, invoiceDetail);
	}

	getById(id: number): Observable<InvoiceDetail> {
		return this.http.get<any>(InvoiceDetailService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceDetailService.BASE_URL, {params: params});
	}


	// Price se setting in the backend
	initHarvestDetail(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			id: new FormControl(invoiceDetail.id),
			invoice: new FormControl(invoiceDetail.invoice),
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			farm:  new FormControl(invoiceDetail.lot.farm ? invoiceDetail.lot.farm.id : undefined),
			lot: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			noteInvoiceDetail: new FormControl(invoiceDetail.note, [ Validators.maxLength(100)]),
			nameDelivered: new FormControl(''),
			nameReceived: new FormControl(''),
			startDate: new FormControl(this.dateTimeIso()),
		});
	}

	// Price se setting in the backend
	initPurchaseDetail(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			id: new FormControl(invoiceDetail.id),
			invoice: new FormControl(invoiceDetail.invoice),
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			store: new FormControl(invoiceDetail.store ? invoiceDetail.store.id : undefined , Validators.required),
			costItemType:  new FormControl(invoiceDetail.costItemType,
				[Validators.required, CustomValidators.numberRegex, CustomValidators.min(0)]),
			nameDelivered: new FormControl(invoiceDetail.nameDelivered, [Validators.required, Validators.maxLength(50)]),
			nameReceived: new FormControl(invoiceDetail.nameReceived, [Validators.required, Validators.maxLength(50)]),
			note: new FormControl(invoiceDetail.note, [Validators.maxLength(50)]),
			noteInvoiceDetail: new FormControl(invoiceDetail.note, [Validators.maxLength(50)]),
			startDate: new FormControl(this.dateTimeIso()),
			invoiceDetailPurity: this.fb.array([
				// this.initPurities(invoiceDetail.invoiceDetailPurity))
			])
		});
	}

	// initPurities(purities: InvoiceDetailPurity[]): FormGroup {
	// 	return this.fb.group({
	// 		idPurity: new FormControl(purities.idPurity, Validators.required),
	// 		valueRateInvoiceDetailPurity: new FormControl( purities.valueRateInvoiceDetailPurity , Validators.required),
	// 	});
	// }

	dateTimeIso() {
		let today = new Date();
		let isoDate = today.toISOString();
		let aux = isoDate.split('.');
		return aux[0] + 'Z';
	}
}
