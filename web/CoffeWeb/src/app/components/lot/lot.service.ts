import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';

import { BaseService } from '../../core/base.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { CustomValidators } from '../../core/utils/validator/custom-validator';
import { Lot } from '../../core/models/lot';

@Injectable({
	providedIn: 'root'
})
export class LotService {

	private static readonly BASE_URL: string = BaseService.HOST + '/lot';

	constructor(
		private http: HttpClient,
		private fb: FormBuilder
	) { }

	create(lot: Lot): Observable<Lot> {
		return this.http.post<any>(LotService.BASE_URL, lot);
	}

	update(lot: Lot): Observable<Lot> {
			return this.http.put<any>(LotService.BASE_URL + '/' + lot.id, lot);
	}

	delete(id: number): Observable<any> {
		return this.http.delete<any>(LotService.BASE_URL + '/' + id);
	}

	deletes(ids: {'ids': number[]}): Observable<any> {
	return this.http.post(LotService.BASE_URL + '/deletes', ids );
	}

	getById(id: number): Observable<Lot> {
		return this.http.get<any>(LotService.BASE_URL + '/' + id);
	}

	getAll(params: HttpParams = new HttpParams()): Observable<any> {
			return this.http.get<any>(LotService.BASE_URL, {params: params});
	}

	getLot(lot: Lot): FormGroup {
		return this.fb.group({
			id: new FormControl(lot.id),
			farm: new FormControl(lot.farm, Validators.required),
			nameLot: new FormControl(lot.nameLot, [Validators.required, Validators.maxLength(50)]),
			areaLot:  new FormControl(lot.areaLot, [Validators.required, Validators.maxLength(100)]),
			heighLot: new FormControl(lot.heighLot, [Validators.required, Validators.maxLength(100)]),
			priceLot: new FormControl(lot.priceLot, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			statusLot: new FormControl(lot.statusLot, [Validators.required, Validators.maxLength(100)]),
		});
	}


}


