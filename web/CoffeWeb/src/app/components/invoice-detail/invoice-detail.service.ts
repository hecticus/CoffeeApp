import { BaseService } from '../../core/base.service';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Lot } from '../../core/models/lot';
import { Observable } from 'rxjs/internal/Observable';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';
import { CustomValidators } from 'src/app/core/utils/validator/custom-validator';
import { Purities } from 'src/app/core/models/purities';


@Injectable({
	providedIn: 'root'
})
export class InvoiceDetailService {

	private static readonly BASE_URL: string = BaseService.HOST + '/invoiceDetail';

	constructor(
		private http: HttpClient,
		private fb: FormBuilder,
	) { }

	getById(id: number): Observable<InvoiceDetail> {
		return this.http.get<any>(InvoiceDetailService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceDetailService.BASE_URL, {params: params});
	}

	// Price se setting in the backend
	initInvoiceDetail(invoiceDetail: InvoiceDetail): FormGroup {
		return this.fb.group({
			invoice: new FormControl(invoiceDetail.invoice ? invoiceDetail.invoice.id : undefined),
			amountInvoiceDetail: new FormControl(invoiceDetail.amountInvoiceDetail, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			itemType: new FormControl(invoiceDetail.itemType ? invoiceDetail.itemType.id : undefined , Validators.required),
			store: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			lot: new FormControl(invoiceDetail.lot ? invoiceDetail.lot.id : undefined , Validators.required),
			costItemType:  new FormControl(invoiceDetail.priceItemTypeByLot,
				[Validators.required, CustomValidators.numberRegex, CustomValidators.min(0)]),
			nameDelivered: new FormControl(invoiceDetail.nameDelivered, [Validators.required, Validators.maxLength(50)]),
			nameReceived: new FormControl(invoiceDetail.nameReceived, [Validators.required, Validators.maxLength(50)]),
			note: new FormControl(invoiceDetail.note, [Validators.maxLength(50)]),
			startDate: new FormControl(this.dateTimeIso()),
			invoiceDetailPurity: this.fb.array([
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
