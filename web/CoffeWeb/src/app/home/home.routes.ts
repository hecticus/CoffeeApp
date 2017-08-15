import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { AuthGuard } from '../common/auth.guard';


export const homeRoutes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [AuthGuard], 
        children: [
                 ],
        data: {
            breadcrumb: "home",
            icon: "home"
        },
    },
]; 

export const homeRouting = RouterModule.forChild(homeRoutes);


