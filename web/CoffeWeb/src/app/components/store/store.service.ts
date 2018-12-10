import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { BaseService } from '../../core/base.service';
import { Observable } from 'rxjs';
import { Farm } from '../../core/models/farm';

@Injectable({
	providedIn: 'root'
	})

export class StoreService {
	private static readonly BASE_URL: string = BaseService.HOST + '/store';

	constructor(
		private http: HttpClient
	) { }

	getById(id: number): Observable<Farm> {
		return this.http.get<Farm>( StoreService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams): Observable<any> {
		return this.http.get<Farm[]>(StoreService.BASE_URL + '', {params: params});
	}

}

