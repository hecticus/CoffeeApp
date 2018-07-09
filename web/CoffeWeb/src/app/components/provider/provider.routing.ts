import { Routes, RouterModule } from '@angular/router';
import { ProviderComponent } from './provider.component';
import { ProviderListComponent } from './provider-list.component';
import { ProviderReadComponent } from './provider-read.component';
import { ProviderCreateComponent } from './provider-create.component';
import { ProviderUpdateComponent } from './provider-update.component';
import { NgModule } from '@angular/core';

export const providerRoutes: Routes = [
	{
		path: 'providers',
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
				component:  ProviderCreateComponent,
				data: {
					breadcrumb: undefined
				}
			}, {
				path: ':providerId',
				component:  ProviderReadComponent,
				data: {
					breadcrumb: undefined
				},
				children: [
					{
						path: 'update',
						component:  ProviderUpdateComponent,
						data: {
							breadcrumb: 'update',
							icon: 'edit'
						}
					}, {
						path: '',
						pathMatch: 'full',
						component:  ProviderReadComponent
					}
				]
			}
		]
	}
];

@NgModule({
	imports: [
		RouterModule.forChild(providerRoutes)
	],
	exports: [
		RouterModule
	]
})
export class ProviderRoutingModule { }
