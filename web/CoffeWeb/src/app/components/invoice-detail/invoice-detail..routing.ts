import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { InvoiceDetailReadComponent } from './invoice-detail-read.component';

export const invoiceDetailRoutes: Routes = [
	{
		path: 'invoicesDetails/create',
		component: InvoiceDetailReadComponent,
		data: {
			breadcrumb: 'Invoice Create'
		}
	},
	{
		path: 'invoicesDetails/:invoiceId',
		component: InvoiceDetailReadComponent,
		data: {
			breadcrumb: 'Invoice Detail'
		},
		children: [
			{
				path: 'full',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: 'Detalle'
				}
			},
			{
				path: 'update',
				pathMatch: 'full',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: 'Actualización'
				},
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
