import { LotCreateComponent } from './lot-create.component';
import { Routes } from '@angular/router';
import { LotComponent } from './lot.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotUpdateComponent } from './lot-update.component';
import { ProviderComponent } from '../provider/provider.component';
import { ProviderListComponent } from '../provider/provider-list.component';

export const lotRoutes: Routes = [
	{
		path: 'lot',
		component: ProviderComponent,
		data: {
			breadcrumb: 'lots',
			icon: 'list'
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: ProviderListComponent,
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
						component: LotListComponent
					}
				]
			}
		]
	}
];
