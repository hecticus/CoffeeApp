import { User } from './../models/user';
import { Component, OnInit } from '@angular/core';
import { AccessTokenRequest } from '../models/accessTokenResquest';
import { Router } from '@angular/router';
import { AuthService } from '../../components/auth/auth.service';

@Component({
	selector: 'app-topbar',
	templateUrl: './topbar.component.html',
	styleUrls: ['./topbar.component.css',  ]
})
export class TopbarComponent implements OnInit {

	user: User;

	constructor(
		private router: Router,
		private authService: AuthService
	) { }

	ngOnInit() {
		this.user = JSON.parse(sessionStorage.getItem('user'));
		console.log(sessionStorage.getItem('user'));
	}

	public logout() {
		let accessTokenRequest: AccessTokenRequest = new AccessTokenRequest();
		accessTokenRequest.refresh_token = sessionStorage.getItem('refresh_token');
		this.authService.logout(accessTokenRequest).subscribe(response => {
			sessionStorage.clear();
			this.router.navigate(['/']);
		}); // , (err) => this.notificationService.error());
	}

	public password() {
		this.router.navigate(['/changepassword']);
	}
}
