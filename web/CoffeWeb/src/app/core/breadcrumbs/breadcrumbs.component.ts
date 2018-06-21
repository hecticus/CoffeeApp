import { Component, OnInit } from '@angular/core';

interface IBreadcrumb {
  label: string;
  // params?: Params;
  url: string;
  icon: string;
}

@Component({
  selector: 'app-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.css']
})
export class BreadcrumbsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

 /*  public breadcrumbs: IBreadcrumb[];

  constructor(
  ) {
    this.breadcrumbs = [];
  }

  ngOnInit() {
    this.breadcrumbs = this.getBreadcrumbs(this.activatedRoute);

    this.router.events
      .filter((event) => event instanceof NavigationEnd)
      .subscribe((event) => {
        this.breadcrumbs = this.getBreadcrumbs(this.activatedRoute);
      });

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
    return breadcrumbs;
  } */
}
