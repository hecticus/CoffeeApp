import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { Login } from './login';
import { Signup } from './signup';
import { LogoutComponent } from './user/logout/logout.component';
import { AuthGuard } from './common/auth.guard';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { App } from './app';
import { homeRoutes } from './home/home.routes';

export const routes: Routes = [
  { path: '',       component: Login },
  { path: 'login',  component: Login },
  { path: 'signup', component: Signup },
  { path: 'home',   component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'reset',   component: ChangePasswordComponent },
  {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    },{
        path: '',
        component: App,
        children: [
            ...homeRoutes
        ]
    }
 

];
