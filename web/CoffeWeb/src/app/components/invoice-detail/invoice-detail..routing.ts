import { InvoiceDetailListComponent } from '../invoice-detail/invoice-detail-list.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { InvoiceDetailReadComponent } from './invoice-detail-read.component';

export const invoiceDetailRoutes: Routes = [
	{
		path: 'invoicesDetail',
		component: InvoiceDetailReadComponent,
		data: {
			breadcrumb: 'Invoice Detail'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: undefined
				},

			}, {
				path: ':invoiceId',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: 'Read'
				}
			}
		]
	}
];

@NgModule({
	imports: [
		RouterModule.forChild(invoiceDetailRoutes)
	],
	exports: [
		RouterModule
	]
})
export class InvoiceRoutingModule { }
