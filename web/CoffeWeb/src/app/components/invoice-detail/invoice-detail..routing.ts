import { InvoiceDetailListComponent } from './invoice-detail-list.component';
import { InvoiceDetail } from './../../core/models/invoice-detail';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { InvoiceDetailReadComponent } from './invoice-detail-read.component';

export const invoiceDetailRoutes: Routes = [
	{
		path: 'invoicesDetails',
		component: InvoiceDetail,
		data: {
			breadcrumb: 'Invoice Detail'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: InvoiceDetailListComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: 'create',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: 'Crear'
				},
			}, {
				path: ':invoiceId',
				component: InvoiceDetailReadComponent,
				data: {
					breadcrumb: 'Detalle'
				},
				children: [
					{
						path: '',
						pathMatch: 'full',
						component: InvoiceDetailReadComponent,
						data: {
							breadcrumb: undefined
						},
					}
				]
			}	
		]
	}
];


// export const invoiceDetailRoutes: Routes = [
// 	{
// 		path: 'invoicesDetails/:invoiceDetailId',
// 		component: InvoiceDetailReadComponent,
// 		data: {
// 			breadcrumb: 'Invoice Detail'
// 		},
// 		children: [
// 			{
// 				path: '',
// 				pathMatch: 'full',
// 				component: InvoiceDetailReadComponent,
// 				data: {
// 					breadcrumb: undefined
// 				}
// 			}
// 		]
// 	}
// ];

@NgModule({
	imports: [
		RouterModule.forChild(invoiceDetailRoutes)
	],
	exports: [
		RouterModule
	]
})
export class InvoiceDetailRoutingModule { }
