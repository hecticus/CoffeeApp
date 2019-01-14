import { InvoiceDetailComponent } from './invoice-detail.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { InvoiceDetailReadComponent } from './invoice-detail-read.component';
import { InvoiceDetailUpdateComponent } from './invoice-detail-update.component';

export const invoiceDetailRoutes: Routes = [
	{
		path: 'invoicesDetails/create',
		component: InvoiceDetailReadComponent,
		data: {
			breadcrumb: 'Crear Nuevo Item'
		}
	},
	{
		path: 'invoicesDetails/:invoiceDetailId',
		component: InvoiceDetailComponent,
		data: {
			breadcrumb: 'Detalle del Item'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: undefined
				}
			},
			{
				path: 'update',
				pathMatch: 'full',
				component: InvoiceDetailUpdateComponent,
				data: {
					breadcrumb: 'Actualizar'
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
