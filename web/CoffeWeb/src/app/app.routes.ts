import { Routes } from '@angular/router';
import { Home } from './home';
import { Panel } from './panel';
import { Farm } from './farm';
import { Login } from './login';
import { Signup } from './signup';
import { AuthGuard } from './common/auth.guard';

export const routes: Routes = [
  { path: '',       component: Login },
  { path: 'login',  component: Login },
  { path: 'signup', component: Signup },
  { path: 'home',   component: Home, canActivate: [AuthGuard] },
  { path: 'panel',  component: Panel, canActivate: [AuthGuard] },
  { path: 'farm',  component: Farm, canActivate: [AuthGuard] },
  { path: '**',     component: Login },
];
