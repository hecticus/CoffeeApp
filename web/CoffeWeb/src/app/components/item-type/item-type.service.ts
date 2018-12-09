import { ItemType } from './../../core/models/item-type';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseService } from 'src/app/core/base.service';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class ItemTypeService {
	private static readonly BASE_URL: string = BaseService.HOST + '/itemType';

	constructor(
		private http: HttpClient
	) { }


	getById(id: number): Observable<ItemType> {
		return this.http.get<ItemType>( ItemTypeService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams): Observable<any> {
		return this.http.get<ItemType[]>(ItemTypeService.BASE_URL + '', {params: params});
	}

}


