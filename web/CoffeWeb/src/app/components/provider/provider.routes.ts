import { Routes } from '@angular/router';
import { ProviderComponent } from './provider.component';
import { ProviderListComponent } from './provider-list.component';

export const providerRoutes: Routes = [
	{
		path: 'povider',
		component: ProviderComponent,
		data: {
			breadcrumb: 'providers',
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
				component:  ProviderListComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: ':providerId',
				component:  ProviderListComponent,
				data: {
					breadcrumb: undefined
				},
				children: [
					{
						path: 'update',
						component:  ProviderListComponent,
						data: {
							breadcrumb: 'update',
							icon: 'edit'
						}
					}, {
						path: '',
						pathMatch: 'full',
						component:  ProviderListComponent
					}
				]
			}
		]
	}
];