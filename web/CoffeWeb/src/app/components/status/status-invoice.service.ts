import { Injectable } from '@angular/core';
import { BaseService } from '../../core/base.service';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class StatusInvoiceService {
	private static readonly BASE_URL: string = BaseService.HOST + '/statusInvoice';

	constructor(
		private http: HttpClient
	) {}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get<any>(StatusInvoiceService.BASE_URL, {params: params});
	}
}
