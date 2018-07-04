import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable} from '@angular/core';
import { BaseService } from '../../core/base.service';
import { Observable } from 'rxjs';
import { ProviderType } from '../../core/models/provider-type';

@Injectable({
	providedIn: 'root'
})
export class ProviderTypeService {

	private static readonly BASE_URL: string = BaseService.HOST + '/providerType';

	constructor (
		private http: HttpClient,
		private fb: FormBuilder,
	) { }


	create(providerType: ProviderType): Observable<ProviderType> {
		return this.http.post<any>(ProviderTypeService.BASE_URL, providerType);
	}

	update(providerType: ProviderType): Observable<ProviderType> {
			return this.http.put<any>(ProviderTypeService.BASE_URL + '/' + providerType.id, providerType);
	}

	delete(id: number): Observable<any> {
		return this.http.delete<any>(ProviderTypeService.BASE_URL + '/' + id);
	}

	deletes(ids: {'ids': number[]}): Observable<any> {
	return this.http.post(ProviderTypeService.BASE_URL + '/deletes', ids );
	}

	getById(id: number): Observable<ProviderType> {
		return this.http.get<any>(ProviderTypeService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
			return this.http.get<any>(ProviderTypeService.BASE_URL, {params: params});
	}

}
