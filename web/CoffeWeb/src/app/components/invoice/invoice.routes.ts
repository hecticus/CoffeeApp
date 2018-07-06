import { Routes } from '@angular/router';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { InvoiceUpdateComponent } from './invoice-update.component';

export const providerRoutes: Routes = [
	{
		path: 'lot',
		component: InvoiceComponent,
		data: {
			breadcrumb: 'lots',
			icon: 'list'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: InvoiceListComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: 'create',
				component: InvoiceCreateComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: ':invoiceId',
				component: InvoiceReadComponent,
				data: {
					breadcrumb: undefined
				},
				children: [
					{
						path: 'update',
						component: InvoiceUpdateComponent,
						data: {
							breadcrumb: 'update',
							icon: 'edit'
						}
					}, {
						path: '',
						pathMatch: 'full',
						component: InvoiceListComponent
					}
				]
			}
		]
	}
];
