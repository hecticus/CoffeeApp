import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
	selector: 'app-sidebar',
	templateUrl: './sidebar.component.html',
	styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent implements OnInit {
	dataMenuItem: string;
	public readonly NAV_ORDERS = 'orders';
	public readonly NAV_PRODUCTS_SERVICES = 'products_services';
	public readonly NAV_COMPANIES_STORES = 'companies_stores';
	public readonly NAV_USERS = 'users';

	selection = new SelectionModel<string>(true, []);

	constructor(
		private activatedRoute: ActivatedRoute
	) {
		this.selection.select(this.NAV_ORDERS, this.NAV_PRODUCTS_SERVICES, this.NAV_COMPANIES_STORES, this.NAV_USERS);
	}

	ngOnInit() {
		if (this.activatedRoute.firstChild && this.activatedRoute.firstChild.data) {
			this.activatedRoute.firstChild.data.subscribe(data => {
				if (data['menu']) {
					this.dataMenuItem = data['menu'];
				}
			});
		}
	}

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
