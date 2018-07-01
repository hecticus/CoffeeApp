import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';

export const authRoutes: Routes = [
	{
		path: '',
		pathMatch: 'full',
		component: LoginComponent
	},
	{
		path: 'changepassword',
		component: LoginComponent
	}
];
// export const loginRouting = RouterModule.forRoot(loginRoutes);

