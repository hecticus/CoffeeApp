import { LotComponent } from './lot.component';
import { LotCreateComponent } from './lot-create.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotUpdateComponent } from './lot-update.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

export const lotRoutes: Routes = [
	{
		path: 'lots',
		component: LotComponent,
		data: {
			breadcrumb: 'Lotes'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: LotListComponent,
				data: {
					expectedRoles: ['super', 'employee']
				}
			}, {
				path: 'create',
				component: LotCreateComponent,
				data: {
					breadcrumb: 'Crear'
				}
			}, {
				path: ':lotId',
				component: LotComponent,
				data: {
					breadcrumb: 'Detalle'
				},
				children: [
					{
						path: '',
						pathMatch: 'full',
						component: LotReadComponent
					}, {
						path: 'update',
						component: LotUpdateComponent,
						data: {
							breadcrumb: 'Actualizar'
						}
					}
				]
			}
		]
	}
];

@NgModule({
	imports: [
		RouterModule.forChild(lotRoutes)
	],
	exports: [
		RouterModule
	]
})
export class LotRoutingModule { }
