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
			breadcrumb: 'Proveedores',
		},
		children: [
			{
				path: '',
				pathMatch: 'full',
				component: ProviderListComponent
			}, {
				path: 'create',
				component:  ProviderCreateComponent,
				data: {
					breadcrumb: 'Crear'
				}
			}, {
				path: ':providerId',
				component:  ProviderComponent,
				data: {
					breadcrumb: 'Detalle'
				},
				children: [
					{
						path: '',
						pathMatch: 'full',
						component:  ProviderReadComponent
					}, {
						path: 'update',
						component:  ProviderUpdateComponent,
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
		RouterModule.forChild(providerRoutes)
	],
	exports: [
		RouterModule
	]
})
export class ProviderRoutingModule { }
