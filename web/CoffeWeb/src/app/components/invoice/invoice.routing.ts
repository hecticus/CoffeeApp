import { InvoiceDetailListComponent } from '../invoice-detail/invoice-detail-list.component';
import { Routes, RouterModule } from '@angular/router';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { NgModule } from '@angular/core';
import { invoiceDetailRoutes } from '../invoice-detail/invoice-detail..routing';

export const invoiceRoutes: Routes = [
	{
		path: 'invoices',
		component: InvoiceComponent,
		data: {
			breadcrumb: 'Invoices'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: InvoiceListComponent,
				data: {
					breadcrumb: undefined
				},

			}, {
				path: 'create',
				component: InvoiceCreateComponent,
				data: {
					breadcrumb: 'Crear'
				},

			}, {
				path: ':invoiceId',
				component: InvoiceReadComponent,
				data: {
					breadcrumb: 'Detalle'
				},
				children: [
					{
						path: 'update',
						component: InvoiceUpdateComponent,
						data: {
							breadcrumb: 'Actualizar'
						},

					},
					...invoiceDetailRoutes,
					{
						path: '',
						pathMatch: 'full',
						component: InvoiceReadComponent,
						data: {
							breadcrumb: undefined
						},
					},
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
