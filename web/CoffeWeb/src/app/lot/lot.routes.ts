import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LotComponent } from './lot.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotCreateComponent } from './lot-create.component';
import { LotUpdateComponent } from './lot-update.component';

export const lotRoutes: Routes = [
	{
	    path: 'lot',
	    component: LotComponent,
	    data: {
	    	breadcrumb: "lots",
	    	icon: "list"
	    },
	    children:[
		    {
			    path: '',
			    pathMatch: 'full',
			    component: LotListComponent,
			    data: {
			    	breadcrumb: undefined
			    }
			},
			{
			    path: 'create',
			    component: LotCreateComponent,
			  	data: {
			    	breadcrumb: "create",
	    			icon: "create"
			    }  
		  	},
		  	{
			    path: ':lotId',
			    component: LotComponent,
			    data: {
			    	breadcrumb: "detail",
	    			icon: "detail"
			    },
			    children:[
				    {
					    path: '',
					    pathMatch: 'full',
					    component: LotReadComponent,
					    data: {
					    	breadcrumb: undefined
					    } 
					},
					{
					    path: 'update',
					    component: LotUpdateComponent,
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