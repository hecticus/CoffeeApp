import { Routes, RouterModule } from '@angular/router';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { NgModule } from '@angular/core';

export const invoiceRoutes: Routes = [
	{
		path: 'invoices',
		component: InvoiceComponent,
		data: {
			breadcrumb: 'invoices',
			icon: 'invoices'
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
						component: InvoiceReadComponent
					}
				]
			}
		]
	}
];

@NgModule({
	imports: [
		RouterModule.forChild(invoiceRoutes)
	],
	exports: [
		RouterModule
	]
})
export class InvoiceRoutingModule { }
