import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { AuthGuard } from '../common/auth.guard';
import { userRoutes } from '../user/user.routes';
import { lotRoutes } from '../lot/lot.routes';
import { providerRoutes } from '../provider/provider.routes';
import { invoiceRoutes } from '../invoice/invoice.routes';


export const homeRoutes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [AuthGuard],
        children: [
           ...lotRoutes,
           ...providerRoutes,
           ...invoiceRoutes
        ],
        data: {
            breadcrumb: "home",
            icon: "home"
        },
    }, 
      
]; 

export const homeRouting = RouterModule.forChild(homeRoutes);


