import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
	selector: 'app-sidebar',
	templateUrl: './sidebar.component.html',
	styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent implements OnInit {
	dataMenuItem: string;

	constructor() {}

	ngOnInit() {  }

	select(event: any) {
		this.dataMenuItem = event.target.getAttribute('data-menu-item');
	}

	toogleExpand(event: any) {
		const el = event.target;
		if (el.classList.contains('expanded')) {
			el.classList.remove('expanded');
		} else {
			el.classList.add('expanded');
		}
	}
}
