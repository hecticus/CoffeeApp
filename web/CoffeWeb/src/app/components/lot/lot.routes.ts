import { LotCreateComponent } from './lot-create.component';
import { Routes } from '@angular/router';
import { LotComponent } from './lot.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotUpdateComponent } from './lot-update.component';

export const lotRoutes: Routes = [
	{
		path: 'lot',
		component: LotComponent,
		data: {
			breadcrumb: 'lots',
			icon: 'list'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: LotListComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: 'create',
				component: LotCreateComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: ':lotId',
				component: LotUpdateComponent,
				data: {
					breadcrumb: undefined
				},
				children: [
					{
						path: '',
						pathMatch: 'full',
						component: LotListComponent
					},
					{
						path: 'update',
						component: LotUpdateComponent,
						data: {
							breadcrumb: 'update',
							icon: 'edit'
						}
					}
				]
			}
		]
	}
];