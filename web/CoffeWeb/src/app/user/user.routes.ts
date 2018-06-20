import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from '../common/auth.guard';
import { ChangePasswordComponent } from './change-password/change-password.component';


export const userRoutes: Routes = [
    {
        path: '',
        component: LoginComponent,
        children: [ ],
        
    },
    { 
        path: 'login',
        component: LoginComponent
    },  
    { 
        path: 'reset',
        component: ChangePasswordComponent 
    },
]; 

export const userRouting = RouterModule.forChild(userRoutes);
