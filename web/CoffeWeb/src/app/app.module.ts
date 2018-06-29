import { LotModule } from './components/lot/lot.module';


import { AppComponent } from './app.component';
import { BreadcrumbsComponent } from './core/breadcrumbs/breadcrumbs.component';
import { BrowserModule } from '@angular/platform-browser';
import { FarmComponent } from 'src/app/components/farm/farm.component';
import { HttpClientModule } from '@angular/common/http';
import { LotComponent } from './components/lot/lot.component';
import { NgModule } from '@angular/core';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { TopbarComponent } from './core/topbar/topbar.component';
import { HeroesComponent } from './components/heroes/heroes.component';
import { FormsModule } from '@angular/forms';
import { HeroesDetailComponent } from './components/heroes-detail/heroes-detail.component';
import { MessagesComponent } from './components/messages/messages.component';
import { AppRoutingModule } from './/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TableComponent } from './core/table/table.component';
import { TableColumnDirective } from './core/table/tableColumnDirective';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ContextMenuModule } from 'ngx-contextmenu';
import {MatTableModule} from '@angular/material/table';

@NgModule ({
	declarations: [
		AppComponent,
		//  ImportComponets
		FarmComponent,
		// ImportCore
		SidebarComponent,
		TopbarComponent,
		BreadcrumbsComponent,
		LotComponent,
		HeroesComponent,
		HeroesDetailComponent,
		MessagesComponent,
		DashboardComponent,
		TableComponent,
		TableColumnDirective
	],
	imports: [
		BrowserModule,
		HttpClientModule,
		FormsModule,
		RouterModule,
		AppRoutingModule,
		Ng2SmartTableModule,
		LotModule,
		ContextMenuModule.forRoot(),
		MatTableModule,
	],
	providers: [],
	bootstrap: [AppComponent]
})

export class AppModule { }

