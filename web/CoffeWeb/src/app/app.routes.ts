import { ModuleWithProviders } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from './home/home.component';

import { AuthGuard } from './common/auth.guard';

import { App } from './app';
import { homeRoutes } from './home/home.routes';
import { userRoutes } from './user/user.routes';

export const routes: Routes = [
  {
    path: '',
    component: App,
    children:
    [
      ...userRoutes,
      ...homeRoutes
    ]
  },
  {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
  }
  

];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);