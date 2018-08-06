import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
	{
		path: '',
		component: AppComponent,
		children: [
			{
				path: '',
				loadChildren: 'src/app/components/auth/auth.module#AuthModule'
			}, {
				path: 'admin',
				loadChildren: 'src/app/components/home/home.module#HomeModule'
			}
		]
	}, {
		path: '**',
		redirectTo: '',
		pathMatch: 'full'
	}
];

// export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
@NgModule({
	imports: [
		RouterModule.forRoot(appRoutes)
	],
	exports: [
		RouterModule
	]
})
export class AppRoutingModule { }
