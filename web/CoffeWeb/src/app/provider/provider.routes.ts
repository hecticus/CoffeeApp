import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProviderComponent } from './provider.component';
import { ProviderListComponent } from './provider-list.component';
import { ProviderReadComponent } from './provider-read.component';
import { ProviderCreateComponent } from './provider-create.component';
import { ProviderUpdateComponent } from './provider-update.component';

export const providerRoutes: Routes = [
	{
	    path: 'provider',
	    component: ProviderComponent,
	    data: {
	    	breadcrumb: "providers",
	    	icon: "list"
	    },
	    children:[
		    {
			    path: '',
			    pathMatch: 'full',
			    component: ProviderListComponent,
			    data: {
			    	breadcrumb: undefined
			    }
			},
			{
			    path: 'create',
			    component: ProviderCreateComponent,
			  	data: {
			    	breadcrumb: "create",
	    			icon: "create"
			    }  
		  	},
		  	{
			    path: ':providerId',
			    component: ProviderComponent,
			    data: {
			    	breadcrumb: "detail",
	    			icon: "detail"
			    },
			    children:[
				    {
					    path: '',
					    pathMatch: 'full',
					    component: ProviderReadComponent,
					    data: {
					    	breadcrumb: undefined
					    } 
					},
					{
					    path: 'update',
					    component: ProviderUpdateComponent,
					  	data: {
					    	breadcrumb: "update",
	    					icon: "update"
					    }  
				  	}
			  	]
		  	}
	  	]
  	}
];