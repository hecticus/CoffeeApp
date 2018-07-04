import { Injectable } from '@angular/core';
import { BaseService } from '../../core/base.service';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class StatusInvoiceDetailService {
	private static readonly BASE_URL: string = BaseService.HOST + '/statusInvoiceDetail';

	constructor(
		private http: HttpClient
	) {}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get(StatusInvoiceDetailService.BASE_URL, {params: params});
	}
}
