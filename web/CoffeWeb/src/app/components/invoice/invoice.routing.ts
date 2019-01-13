import { InvoiceDetailReadComponent } from './../invoice-detail/invoice-detail-read.component';
import { Routes, RouterModule } from '@angular/router';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { NgModule } from '@angular/core';
import { invoiceDetailRoutes } from '../invoice-detail/invoice-detail..routing';

export const invoiceRoutes: Routes = [
	{
		path: 'invoices',
		component: InvoiceComponent,
		data: {
			breadcrumb: 'Facturas'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: InvoiceListComponent,
				data: {
					breadcrumb: undefined
				},

			},
			{
				path: 'create',
				component: InvoiceCreateComponent,
				data: {
					breadcrumb: 'Crear Factura'
				},

			},
			{
				path: ':invoiceId',
				component: InvoiceComponent,
				data: {
					breadcrumb: 'Detalle de la Factura'
				},
				children: [
					{
						path: '',
						pathMatch: 'full',
						component: InvoiceReadComponent,
						data: {
							breadcrumb: undefined
						},
					},
					...invoiceDetailRoutes,
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
