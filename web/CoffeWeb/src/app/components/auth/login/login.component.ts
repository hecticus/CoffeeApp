import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CustomValidators } from '../../../core/utils/validator/custom-validator';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';
import { AuthorizationRequest } from '../../../core/models/authorizationRequest';
import { UserService } from '../../user/user.service';
import { NotificationService } from '../../../core/notification/notification.service';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css'],
	encapsulation: ViewEncapsulation.None
})

export class LoginComponent implements OnInit {
	form: FormGroup;
	authorizationRequest: AuthorizationRequest;

	constructor(
		private authService: AuthService,
		private userService: UserService,
		private activatedRoute: ActivatedRoute,
		private notificationService: NotificationService,
		private router: Router,
		private fb: FormBuilder,
		public toastr: ToastrManager
	) {
		this.form = this.getForm();
	}

	ngOnInit() {
		this.authorizationRequest = new AuthorizationRequest();
		this.authorizationRequest.grant_type = 'password';
		this.authorizationRequest.client_id = 'web_site';
		this.form = this.toFormGroup();
	}

	login(): void {
		if (this.form.valid) {
			this.authorizationRequest.username = this.form.value['email'];
			this.authorizationRequest.password = this.form.value['password'];

			this.authService.login(this.authorizationRequest).subscribe(authorizationResponse => {
				sessionStorage.setItem('token', authorizationResponse.access_token);
				sessionStorage.setItem('refresh_token', authorizationResponse.refresh_token);

				this.userService.getByAuthUserId(authorizationResponse.user_id).subscribe(user => {
					sessionStorage.setItem('user', JSON.stringify(user));
					window.location.href = window.location.href + 'admin' ;
					this.notificationService.nerror.content = 'hola';
					this.notificationService.nerror.title = 'puto';
				}, error => this.toastr.errorToastr('This is error toast.', error));
				// }, err => this.toastr.errorToastr.showError());
			// }); // , (err) => this.notificationService.error());
			}); // , (err) => this.notificationService.error());
		}
	}

	toFormGroup(): FormGroup {
		return this.fb.group({
			email: new FormControl('', [Validators.required, CustomValidators.emailRegex, Validators.maxLength(100)]),
			password: new FormControl('', Validators.required)
		});
	}

	main() {
		console.log('estoy log');
		this.router.navigate(['./admin'], {relativeTo: this.activatedRoute});
	}

	getForm(): FormGroup {
		return this.fb.group({
			email: new FormControl('', [Validators.required, CustomValidators.emailRegex, Validators.maxLength(50)]),
			password: new FormControl('', Validators.required)
		});
	}

	create() {
		this.router.navigate(['./admin'], {relativeTo: this.activatedRoute});
	}

}
