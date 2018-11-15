import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd, Params, PRIMARY_OUTLET } from '@angular/router';
import { filter } from 'rxjs/operators';

interface IBreadcrumb {
	label: string;
	params?: Params;
	url: string;
	icon: string;
}

@Component({
	selector: 'app-breadcrumbs',
	templateUrl: './breadcrumbs.component.html',
	styleUrls: ['./breadcrumbs.component.css']
})
export class BreadcrumbsComponent implements OnInit {

	public breadcrumbs: IBreadcrumb[];

	constructor(
		private activatedRoute: ActivatedRoute,
		private router: Router
	) {
		this.breadcrumbs = [];
	}

	ngOnInit() {
		this.breadcrumbs = this.getBreadcrumbs(this.activatedRoute);

		this.router.events
			.pipe(
				filter((event) => event instanceof NavigationEnd)
			)
			.subscribe((event) => {
				this.breadcrumbs = this.getBreadcrumbs(this.activatedRoute);
			});

		// reference router events: https://toddmotto.com/dynamic-page-titles-angular-2-router-events
		/*this.router.events
			.filter((event) => event instanceof NavigationEnd)
			.map(() => this.activatedRoute)
			.map((route) => {
				while (route.firstChild) {
					route = route.firstChild;
				}
				return route;
			})
			.filter((route) => route.outlet === 'primary')
			.mergeMap((route) => route.data)
			.subscribe((event) => {
				console.log('NavigationEnd:', event);
			});*/
	}

	private getBreadcrumbs(route: ActivatedRoute): IBreadcrumb[] {
		const ROUTE_DATA_BREADCRUMB = 'breadcrumb';
		let  url = '';
		let breadcrumbs: IBreadcrumb[] = [];

		while (route) {
			if (route.outlet === PRIMARY_OUTLET && route.routeConfig.path !== '') {

				let routeURL: string = route.snapshot.url.map(segment => segment.path).join('/');
				// append route URL to URL
				url += `/${routeURL}`;

				let breadcrumb: IBreadcrumb = {
					label: route.snapshot.data[ROUTE_DATA_BREADCRUMB],
					params: route.snapshot.params,
					url: url,
					icon: route.snapshot.data['icon'] ? route.snapshot.data['icon'] : ''
				};
				breadcrumbs.push(breadcrumb);
			}
			route = route.firstChild;
		}

		// we should never get here, but just in case
		return breadcrumbs;
	}

	/*private getBreadcrumbs(route: ActivatedRoute, url = '', breadcrumbs: IBreadcrumb[] = []): IBreadcrumb[] {
		const ROUTE_DATA_BREADCRUMB = 'breadcrumb';
		let children: ActivatedRoute[] = route.children;

		if (children.length === 0) {
			return breadcrumbs;
		}
		console.log(route)

		for (let child of children) {

			// verify primary route
			if (child.outlet !== PRIMARY_OUTLET) {
				continue;
			}

			// (custom) ignore if the route doesn't have data or data[breadcrumbs]
			if (child.snapshot.data[ROUTE_DATA_BREADCRUMB] == null) {
				continue;
			}

			// verify the custom data property "breadcrumb" is specified on the route
			if (!child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
				return this.getBreadcrumbs(child, url, breadcrumbs);
			}

			// get the route's URL segment
			let routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');

			// append route URL to URL
			url += `/${routeURL}`;
			// add breadcrumb
			let breadcrumb: IBreadcrumb = {
				label: child.snapshot.data[ROUTE_DATA_BREADCRUMB],
				params: child.snapshot.params,
				url: url,
				icon: child.snapshot.data['icon'] ? child.snapshot.data['icon'] : ''
			};
			breadcrumbs.push(breadcrumb);

			return this.getBreadcrumbs(child, url, breadcrumbs);
		}

		// we should never get here, but just in case
		return breadcrumbs;
	}*/
}
