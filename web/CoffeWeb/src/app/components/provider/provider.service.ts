import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable} from '@angular/core';
import { BaseService } from '../../core/base.service';
import { Observable } from 'rxjs';
import { Provider } from '../../core/models/provider';
import { CustomValidators } from '../../core/utils/validator/custom-validator';

@Injectable({
	providedIn: 'root'
})

export class ProviderService {

	private static readonly BASE_URL: string = BaseService.HOST + '/provider';

	constructor (
		private http: HttpClient,
		private fb: FormBuilder,
	) { }


	create(provider: Provider): Observable<Provider> {
		return this.http.post<any>(ProviderService.BASE_URL, provider);
	}

	update(provider: Provider): Observable<Provider> {
			return this.http.put<any>(ProviderService.BASE_URL + '/' + provider.id, provider);
	}

	delete(id: number): Observable<any> {
		return this.http.delete<any>(ProviderService.BASE_URL + '/' + id);
	}

	deletes(ids: {'ids': number[]}): Observable<any> {
	return this.http.post(ProviderService.BASE_URL + '/deletes', ids );
	}

	getById(id: number): Observable<Provider> {
		return this.http.get<any>(ProviderService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
			return this.http.get<any>(ProviderService.BASE_URL, {params: params});
	}

	getLot(provider: Provider): FormGroup {
		return this.fb.group({
			id: new FormControl(provider.id),
			providerType: new FormControl(provider.providerType, Validators.required),
			nitProvider: new FormControl(provider.nitProvider, [Validators.required, Validators.maxLength(100)]),
			nameProvider:  new FormControl(provider.nameProvider, [Validators.required, Validators.maxLength(60)]),
			addressProvider: new FormControl(provider.addressProvider, [Validators.required, Validators.maxLength(60)]),
			numberProvider: new FormControl(provider.numberProvider, [CustomValidators.numberRegex, Validators.maxLength(20)]),
			emailProvider: new FormControl(provider.emailProvider, [Validators.required, CustomValidators.match('email')]),
			photoProvider: new FormControl(provider.photoProvider),
			contactNameProvider: new FormControl(provider.contactNameProvider, [Validators.required, Validators.maxLength(50)]),
			statusProvider: new FormControl(provider.statusProvider, [Validators.required, Validators.maxLength(20)]),
			// invoices: new FormControl(provider.emailProvider, [Validators.required, Validators.maxLength(100)]),
		});
	}
}
