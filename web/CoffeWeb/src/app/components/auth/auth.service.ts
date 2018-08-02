import { Injectable } from '@angular/core';
import { BaseService } from '../../core/base.service';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { JwtHelper } from 'angular2-jwt';
import { AuthorizationRequest } from '../../core/models/authorizationRequest';
import { Observable } from 'rxjs/internal/Observable';
import { AccessTokenResponse } from '../../core/models/accessTokenResponce';
import { AccessTokenRequest } from '../../core/models/accessTokenResquest';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	private static readonly BASE_URL: string = BaseService.HOST + '/oauth';
	// private static readonly BASE_URL: string = BaseService.HOST + '/oauth/token';
	private jwtHelper: JwtHelper = new JwtHelper();
	private headers: HttpHeaders;

	constructor(private http: HttpClient) {
		this.headers = new HttpHeaders();
		this.headers.set('Content-Type', 'application/x-www-form-urlencoded');
	}

	login(authorizationRequest: AuthorizationRequest): Observable<AccessTokenResponse> {
		const body = new HttpParams()
			.set('grant_type', authorizationRequest.grant_type)
			.set('username', authorizationRequest.username)
			.set('password', authorizationRequest.password)
			.set('client_id', authorizationRequest.client_id);
		return this.http.post<any>(AuthService.BASE_URL + '/token', body, {headers: this.headers});
			// .catch(BaseService.handleError);
	}

	logout(accessTokenRequest: AccessTokenRequest) {
		const body = new HttpParams()
			.set('refresh_token', accessTokenRequest.refresh_token);
		return this.http.post(AuthService.BASE_URL + '/revokeToken', body, {headers: this.headers});
			// .catch(BaseService.handleError);
	}

	// forgotPassword(email: string): Observable<boolean> {
	// 	return this.http.get(AuthService.BASE_URL + '/reset/' + email);
	// 		// .catch(BaseService.handleError);
	// }

	changePassword(email: string, password: string, oldpassword: string): Observable<Boolean> {
		// console.log('NewPass '+ JSON.stringify({ email: email, password: Global.toMD5(password)}));
		return this.http.put<any>(
			AuthService.BASE_URL + '/changepassword',
			JSON.parse(JSON.stringify({ email: email, password: password, oldpassword: oldpassword})));
			// .catch(BaseService.handleError);
	}

	getPayload(token: string) {
		let decodedToken = this.jwtHelper.decodeToken(token);
		return decodedToken;
	}

}
