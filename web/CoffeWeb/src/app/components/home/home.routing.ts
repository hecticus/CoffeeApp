import { providerRoutes } from './../provider/provider.routing';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { NgModule } from '@angular/core';
import { ProviderRoutingModule } from '../provider/provider.routing';
import { invoiceRoutes } from '../invoice/invoice.routing';
import { lotRoutes } from '../lot/lot.routing';

const homeRoutes: Routes = [
	{
		path: 'admin',
		component: HomeComponent,
		children: [
			{
				path: '',
				component: HomeComponent,
				data: {
					breadcrumb: '',
					icon: ''
				}
			},
			// , {
			// 	path: 'providers',
			// 	loadChildren: 'src/app/components/provider/provider.module#ProviderModule'
			// }, {
			// 	path: 'lots',
			// 	loadChildren: 'src/app/components/lot/lot.module#LotModule'
			// }, {
			// 	path: 'invoices',
			// 	loadChildren: 'src/app/components/invoice/invoice.module#InvoiceModule'
			// }
			...providerRoutes,
			...lotRoutes,
			...invoiceRoutes,
		],
		data: {
			breadcrumb: 'home',
			icon: 'home'
		},
	},
];

// export const homeRouting = RouterModule.forChild(homeRoutes);
@NgModule({
	imports: [
		RouterModule.forChild(homeRoutes)
	],
	exports: [
		RouterModule
	]
})
export class HomeRoutingModule { }
