import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { lotRoutes } from './../lot/lot.routes';
import { providerRoutes } from '../provider/provider.routes';


export const homeRoutes: Routes = [
	{
		path: 'admin',
		component: HomeComponent,
		children: [
			{
				path: '',
				component: HomeComponent,
				data: {
					breadcrumb: '',
					icon: ''
				}
			},
			...lotRoutes,
			...providerRoutes
		],
		data: {
			breadcrumb: 'home',
			icon: 'home'
		},
	},
];

export const homeRouting = RouterModule.forChild(homeRoutes);
