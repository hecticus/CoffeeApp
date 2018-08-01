import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';

const authRoutes: Routes = [
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

@NgModule({
	imports: [
		RouterModule.forChild(authRoutes)
	],
	exports: [
		RouterModule
	]
})
export class AuthRoutingModule { }
