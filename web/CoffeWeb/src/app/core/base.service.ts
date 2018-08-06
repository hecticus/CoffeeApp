import { HttpParams, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
// tslint:disable-next-line:import-blacklist
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

/*
reference httpClient: http://blog.enriqueoriol.com/2017/11/httpclient-vs-http-angular.html
*/
export class BaseService {
	public static readonly HOST: string = environment.backendUrl;

	public static authorizationHeader(): HttpHeaders {
		const headers = new HttpHeaders();
		headers.append('Authorization', 'bearer ' + sessionStorage.getItem('token'));
		return headers;
	}

	public static extractData(res: any) {
		return res.result;
	}

	public static handleError (error: HttpErrorResponse | any) {
		if (error instanceof Error) {
			// A client-side or network error occurred. Handle it accordingly.
			console.log('An error occurred:', error.message ? error.message : error.toString());
		} else {
			// The backend returned an unsuccessful response code.
			// The response body may contain clues as to what went wrong,
			console.log(`Backend returned code ${error.status} - ${error.statusText}, body was: ${error.error.message}`);
		}
		return Observable.throw(error);
	}

	public static jsonToHttpParams(data: {}): HttpParams {
		let httpParams = new HttpParams();
		Object.keys(data).forEach(function (key) {
			httpParams = httpParams.append(key, data[key]);
		});
		return httpParams;
	}
}
