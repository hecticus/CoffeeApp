import { InvoiceDetailListComponent } from '../invoice-detail/invoice-detail-list.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { InvoiceDetailReadComponent } from './invoice-detail-read.component';

export const invoiceDetailRoutes: Routes = [
	{
		path: 'invoicesDetails/:invoiceDetailId',
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
export class InvoiceDetailRoutingModule { }
