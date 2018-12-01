import { InvoiceComponent } from '../invoice/invoice.component';
import { ProviderComponent } from '../provider/provider.component';
import { providerRoutes } from '../provider/provider.routing';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { NgModule } from '@angular/core';
import { invoiceRoutes } from '../invoice/invoice.routing';
import { lotRoutes } from '../lot/lot.routing';
import { InvoiceListComponent } from '../invoice/invoice-list.component';

const homeRoutes: Routes = [
	{
		path: 'admin',
		component: HomeComponent,
		children: [
			{
				path: '',
				redirectTo: '/admin/invoices',
				pathMatch: 'full',
				data: {
					breadcrumb: '',
					icon: '',
					expectedRoles: ['super', 'employee']
				}
			},
			...providerRoutes,
			...lotRoutes,
			...invoiceRoutes,
		],
		data: {
			breadcrumb: 'home'
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
