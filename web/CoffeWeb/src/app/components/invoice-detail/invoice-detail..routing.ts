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
			breadcrumb: 'Invoice Create'
		}
	},
	{
		path: 'invoicesDetails/:invoiceDetailId',
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
				component: InvoiceDetailUpdateComponent,
				data: {
					breadcrumb: 'Actualización'
				},
			}
		]
	}
];

// export const invoiceDetailRoutes: Routes = [
// 	{
// 		path: 'invoicesDetails',
// 		component: InvoiceDetailComponent,
// 		data: {
// 			breadcrumb: 'Detalle de la Factura'
// 		},
// 		children: [
// 			{
// 				path: 'invoicesDetails/create',
// 				component: InvoiceDetailReadComponent,
// 				data: {
// 					breadcrumb: 'Crear Nuevo Item'
// 				}
// 			},
// 			{
// 				path: 'invoicesDetails/:invoiceDetailId',
// 				component: InvoiceDetailReadComponent,
// 				data: {
// 					breadcrumb: 'Detalle del Item'
// 				},
// 				children: [
// 					{
// 						path: 'full',
// 						component: InvoiceDetailReadComponent,
// 						data: {
// 							breadcrumb: 'Detalle del Item'
// 						}
// 					},
// 					{
// 						path: 'update',
// 						pathMatch: 'full',
// 						component: InvoiceDetailUpdateComponent,
// 						data: {
// 							breadcrumb: 'Actualizar item'
// 						},
// 					}
// 				]
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
