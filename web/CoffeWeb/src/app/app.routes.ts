import { Routes } from '@angular/router';
import { Home } from './home';
import { Login } from './login';
import { Signup } from './signup';
import { AuthGuard } from './common/auth.guard';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { App } from './app';

export const routes: Routes = [
  { path: '',       component: Login },
  { path: 'login',  component: Login },
  { path: 'signup', component: Signup },
  { path: 'home',   component: Home, canActivate: [AuthGuard] },
  { path: 'reset',   component: ChangePasswordComponent },
  {
        path: '',
        component: App,
        children: [

        ]
    },{
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }

];
