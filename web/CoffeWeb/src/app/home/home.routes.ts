import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { App } from '../app';
import { HomeComponent } from './home.component';


export const homeRoutes: Routes = [
    {
        path: 'admin',
        component: HomeComponent,
        children: [
                 ],
        data: {
            breadcrumb: "home",
            icon: "home"
        },
    },
]; 

export const homeRouting = RouterModule.forRoot(homeRoutes);


