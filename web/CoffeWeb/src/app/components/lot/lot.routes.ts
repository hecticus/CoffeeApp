import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
			}
		]
	}
];
