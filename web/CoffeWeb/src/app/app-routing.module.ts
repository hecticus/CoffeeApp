import { HeroesDetailComponent } from './components/heroes-detail/heroes-detail.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HeroesComponent } from './components/heroes/heroes.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { lotRoutes } from './components/lot/lot.routes';

const routes: Routes = [
	{
		path: 'heroes', component: HeroesComponent
	},
	{
		path: 'dashboard', component: DashboardComponent
	},
	{
		path: 'detail/:id', component: HeroesDetailComponent
	},
	/*{
		path: '', redirectTo: '/dashboard', pathMatch: 'full'
	},*/
	...lotRoutes
];

@NgModule({
	imports: [ RouterModule.forRoot(routes) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule {}
