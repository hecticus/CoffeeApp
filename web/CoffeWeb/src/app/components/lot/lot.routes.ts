import { LotComponent } from './lot.component';
import { LotCreateComponent } from './lot-create.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotUpdateComponent } from './lot-update.component';
import { Routes } from '@angular/router';

export const lotRoutes: Routes = [
	{
		path: 'lots',
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
				component: LotReadComponent,
				data: {
					breadcrumb: undefined
				},
				children: [
					{
						path: 'update',
						component: LotUpdateComponent,
						data: {
							breadcrumb: 'update',
							icon: 'edit'
						}
					}, {
						path: '',
						pathMatch: 'full',
						component: LotReadComponent
					}
				]
			}
		]
	}
];
