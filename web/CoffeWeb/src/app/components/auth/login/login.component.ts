import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CustomValidators } from '../../../core/utils/validator/custom-validator';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css'],
	encapsulation: ViewEncapsulation.None
})

export class LoginComponent implements OnInit {
	form: FormGroup;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private fb: FormBuilder
	) {
		this.form = this.getForm();
	}

	ngOnInit() {
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

	login() {
		console.log('jjjj');
	}

	showDialogo() {
		console.log('jjjj');
		this.router.navigate(['./admin'], {relativeTo: this.activatedRoute});
	}
	create() {
		this.router.navigate(['./admin'], {relativeTo: this.activatedRoute});
	}

}
