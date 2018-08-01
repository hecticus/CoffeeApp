import { Injectable } from '@angular/core';
import { BaseService } from '../../core/base.service';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class StatusProviderService {
	private static readonly BASE_URL: string = BaseService.HOST + '/statusProvider';

	constructor(
		private http: HttpClient
	) {}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
		return this.http.get(StatusProviderService.BASE_URL, {params: params});
	}
}
