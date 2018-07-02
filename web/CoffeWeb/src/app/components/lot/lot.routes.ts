import { LotCreateComponent } from './lot-create.component';
import { Routes } from '@angular/router';
import { LotComponent } from './lot.component';
import { LotListComponent } from './lot-list.component';

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
				component: LotListComponent,
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
						component: LotListComponent,
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
