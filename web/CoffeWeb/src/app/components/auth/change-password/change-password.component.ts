import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-change-password',
	templateUrl: './change-password.component.html',
	styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

	title = 'Cambiar Contraseña';
	resetForm: FormGroup;
	private email: string;

	logged = false;

	constructor(
		@Inject(FormBuilder) fb: FormBuilder,
		private authService: AuthService,
		private router: Router,
		private route: ActivatedRoute
		) {
		this.resetForm = fb.group({
			oldPassword: ['', Validators.required],
			newPassword: ['', Validators.required],
			confirmPassword: ['', Validators.required]
		});
	}

	ngOnInit() {
		this.logged = sessionStorage.getItem('token') != null ? true : false;
		if (!this.logged) {
			// this.notificationService.error();
			this.router.navigateByUrl('/');
			sessionStorage.clear();
			return;
		}
		this.email = JSON.parse(sessionStorage.getItem('user')).email;
	}

	reset($event): void {
		let oldPass: string = this.getValue('oldPassword');
		let newPass: string = this.getValue('newPassword');
		let confirmPass: string = this.getValue('confirmPassword');
		if (this.isValid('newPassword') && this.isValid('confirmPassword')) {
			if (newPass === confirmPass) {
				this.authService.changePassword(this.email, newPass, oldPass)
					.subscribe(reset => {
						// this.notificationService.sucessUpdate('password');
						if (reset) { this.router.navigate(['/admin']); }
					}); // , (err) => this.notificationService.error());
			}
		}
	}

	isValid (parameter: string) {
		return !this.resetForm.get(parameter).invalid;
	}

	getValue (parameter: string) {
		return this.resetForm.get(parameter).value;
	}
}
