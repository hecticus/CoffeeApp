import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'app-topbar',
	templateUrl: './topbar.component.html',
	styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

	user: String = 'User';

	constructor() { }

	ngOnInit() {
	}

	public logout() { }
	public password() { }
}
