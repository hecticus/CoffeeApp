import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BaseService } from '../../core/base.service';
import { Observable } from 'rxjs/internal/Observable';
import { Lot } from '../../core/models/lot';

@Injectable({
  providedIn: 'root'
})
export class LotService {

  private static readonly BASE_URL: string = BaseService.HOST + '/farm';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder
  ) { }

  create(lot: Lot): Observable<Lot> {
    return this.http.post<Lot>(LotService.BASE_URL, lot);
  }

  update(lot: Lot): Observable<Lot> {
      return this.http.put<Lot>(LotService.BASE_URL + '/' + lot.id, lot);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(LotService.BASE_URL + '/' + id);
  }

  deletes(ids: {'ids': number[]}): Observable<any> {
  return this.http.post(LotService.BASE_URL + '/deletes', ids );
  }

  getById(id: number): Observable<Lot> {
    return this.http.get<Lot>(LotService.BASE_URL + '/' + id);
  }

  getAll(params: HttpParams): Observable<Lot[]> {
    return this.http.get<Lot[]>(LotService.BASE_URL + '/search', {params});
  }

  getAllSearch(params: HttpParams): Observable<Lot[]> {
      return this.http.get<Lot[]>(LotService.BASE_URL + '/search', {params});
  }


}


