import { ModuleWithProviders } from '@angular/core';
import { AppComponent } from './app.component';
import { authRoutes } from './components/auth/auth.routes';
import { homeRoutes } from './components/home/home.routes';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
	{
		path: '',
		component: AppComponent,
		children: [
			...authRoutes,
			...homeRoutes,
		]
	}, {
		path: '**',
		redirectTo: '',
		pathMatch: 'full'
	}
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
