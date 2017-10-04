import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InvoiceComponent } from './invoice.component';
import { InvoiceListComponent } from './invoice-list.component';


export const invoiceRoutes: Routes = [
	{
	    path: 'invoice',
	    component: InvoiceComponent,
	    data: {
	    	breadcrumb: "Ordenes",
	    	icon: "list"
	    },
	    children:[
		    {
			    path: '',
			    pathMatch: 'full',
			    component: InvoiceListComponent,
			    data: {
			    	breadcrumb: undefined
			    }
			},
		/*	{
			    path: 'create',
			    component: InvoiceCreateComponent,
			  	data: {
			    	breadcrumb: "create",
	    			icon: "create"
			    }  
		  	},
		  	{
			    path: ':providerId',
			    component: InvoiceComponent,
			    data: {
			    	breadcrumb: "detail",
	    			icon: "detail"
			    },
			    children:[
				    {
					    path: '',
					    pathMatch: 'full',
					    component: InvoiceReadComponent,
					    data: {
					    	breadcrumb: undefined
					    } 
					},
					{
					    path: 'update',
					    component: InvoiceUpdateComponent,
					  	data: {
					    	breadcrumb: "update",
	    					icon: "update"
					    }  
				  	}
			  	]
		  	}*/
	  	]
  	}
];