import { BaseService } from '../../core/base.service';
import { FormBuilder } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Lot } from '../../core/models/lot';
import { Observable } from 'rxjs/internal/Observable';
import { InvoiceDetail } from 'src/app/core/models/invoice-detail';


@Injectable({
	providedIn: 'root'
})
export class InvoiceDetailService {

	private static readonly BASE_URL: string = BaseService.HOST + '/invoiceDetail';

	constructor(
		private http: HttpClient,
	) { }

	getById(id: number): Observable<InvoiceDetail> {
		return this.http.get<any>(InvoiceDetailService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(InvoiceDetailService.BASE_URL, {params: params});
	}

}
