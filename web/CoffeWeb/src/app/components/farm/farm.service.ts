import { Injectable } from '@angular/core';
import { HttpClient } from 'selenium-webdriver/http';
import { BaseService } from '../../core/base.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FarmService {
  private static readonly BASE_URL: string = BaseService.HOST + '/farm';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder
  ) { }

  getById(id: number): Observable<Farm> {
    return this.http.get( FarmService.BASE_URL + '/' + id, {headers: BaseService})
        .catch(BaseService.handleError);
  }

  getAll(params: HttpParams): Observable<any> {
    return this.http.get(this.urlFarm+'', requestOptions)
        .map(this.extractDataFull)
        .catch(this.handleError);
  }

  getAllSearch(requestOptions: RequestOptions = new RequestOptions()): Observable<Farm[]>{
    requestOptions.headers = contentHeaders;
    // console.log(requestOptions);
    return this.http.get(this.urlFarm + '/search', requestOptions)
        .map(this.extractDataFull)
        .catch(this.handleError);
  }
}

