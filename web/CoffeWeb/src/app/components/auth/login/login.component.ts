import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomValidators } from 'src/app/core/utils/validator/custom-validator';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
    form: FormGroup;

	constructor(
        private router: Router,
		private fb: FormBuilder
    ) {
        this.form = this.getForm();
    }

	ngOnInit() {
	}

    getForm(): FormGroup {
		return this.fb.group({
			email: new FormControl('', [Validators.required, CustomValidators.emailRegex, Validators.maxLength(50)]),
			password: new FormControl('', Validators.required)
		});
	}
}
