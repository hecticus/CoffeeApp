import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BaseService } from '../../core/base.service';
import { Observable } from 'rxjs/internal/Observable';
import { CustomValidators } from '../../core/utils/validator/custom-validator';
import { User } from '../../core/models/user';

@Injectable()
export class UserService {
	private static readonly BASE_URL: string = BaseService.HOST + '/users';

	constructor(
		private http: HttpClient,
		private fb: FormBuilder
	) {}

	getById(id: number): Observable<User> {
		return this.http.get<User>(UserService.BASE_URL + '/' + id, {headers: BaseService.authorizationHeader()});
			// // .map(BaseService.extractData)
			// .catch(BaseService.handleError);
	}

	getByAuthUserId(authUserId: number): Observable<User> {
		return this.http.get<User>(UserService.BASE_URL + '/authUser/' + authUserId, {headers: BaseService.authorizationHeader()});
			// .map(BaseService.extractData)
			// .catch(BaseService.handleError);
	}

	getByEmail(email: string): Observable<User> {
		return this.http.get<User>(UserService.BASE_URL + '/email/' + email, {headers: BaseService.authorizationHeader()});
			// .map(BaseService.extractData)
			// .catch(BaseService.handleError);
	}

	// updatePassword(user: User): Observable<User> {
	// 	return this.http.patch(`${UserService.BASE_URL}/${user.id}/password`, user, {headers: BaseService.authorizationHeader()});
	// 		// .map(BaseService.extractData)
	// 		// .catch(BaseService.handleError);
	// }

	toFormGroup(user: User): FormGroup {
		return this.fb.group({
			id: new FormControl(user.id),
			firstName: new FormControl(user.firstName, [Validators.required, Validators.maxLength(100)]),
			lastName: new FormControl(user.lastName, [Validators.required, Validators.maxLength(100)]),
			description: new FormControl(user.description),
			authUser: this.fb.group({
				email: new FormControl(user.authUser.email, [Validators.required, CustomValidators.emailRegex]),
				emailConfirm: new FormControl(user.authUser.email, [Validators.required, CustomValidators.match('email')]),
				password: new FormControl(user.authUser.password, Validators.required),
				passwordConfirm: new FormControl('', [Validators.required, CustomValidators.match('password')])
			}),
			contact: this.fb.group({
				phone: new FormControl(user.contact.phone, Validators.maxLength(100)),
				phone2: new FormControl(user.contact.phone2, Validators.maxLength(100)),
				latitude: new FormControl(user.contact.latitude, CustomValidators.numberRegex),
				longitude: new FormControl(user.contact.longitude, CustomValidators.numberRegex),
				address: new FormControl(user.contact.address)
			})
		});
	}

	toFormGroupUpdate(user: User): FormGroup {
		return this.fb.group({
			id: new FormControl(user.id),
			firstName: new FormControl(user.firstName, [Validators.required, Validators.maxLength(100)]),
			lastName: new FormControl(user.lastName, [Validators.required, Validators.maxLength(100)]),
			description: new FormControl(user.description),
			authUser: this.fb.group({
				email: new FormControl(user.authUser.email, [Validators.required, CustomValidators.emailRegex]),
				emailConfirm: new FormControl(user.authUser.email, [Validators.required, CustomValidators.match('email')])
			}),
			contact: this.fb.group({
				phone: new FormControl(user.contact.phone, Validators.maxLength(100)),
				phone2: new FormControl(user.contact.phone2, Validators.maxLength(100)),
				latitude: new FormControl(user.contact.latitude, CustomValidators.numberRegex),
				longitude: new FormControl(user.contact.longitude, CustomValidators.numberRegex),
				address: new FormControl(user.contact.address)
			})
		});
	}

	toFormGroupUpdatePassword(user: User): FormGroup {
		return this.fb.group({
			id: new FormControl(user.id),
			authUser: this.fb.group({
				password: new FormControl('', Validators.required),
				passwordConfirm: new FormControl('', [Validators.required, CustomValidators.match('password')])
			}),
		});
	}
}
