import { MatCheckboxModule } from '@angular/material/checkbox';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { BreadcrumbsComponent } from './core/breadcrumbs/breadcrumbs.component';
import { BrowserModule } from '@angular/platform-browser';
import { ContextMenuModule } from 'ngx-contextmenu';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FarmComponent } from 'src/app/components/farm/farm.component';
import { FormsModule } from '@angular/forms';
import { HeroesComponent } from './components/heroes/heroes.component';
import { HeroesDetailComponent } from './components/heroes-detail/heroes-detail.component';
import { HttpClientModule } from '@angular/common/http';
import { LotComponent } from './components/lot/lot.component';
import { LotModule } from './components/lot/lot.module';
import { MatPaginatorModule, MatSelectModule } from '@angular/material';
import { MatTableModule } from '@angular/material/table';
import { MessagesComponent } from './components/messages/messages.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { TableColumnDirective } from './core/table/tableColumnDirective';
import { TableComponent } from './core/table/table.component';
import { TopbarComponent } from './core/topbar/topbar.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HomeComponent } from './components/home/home.component';

import { HomeModule } from './components/home/home.module';
import { AuthModule } from './components/auth/auth.module';

@NgModule({
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
	TableColumnDirective,
	HomeComponent
],
imports: [
	BrowserModule,
	BrowserAnimationsModule,
	HttpClientModule,
	FormsModule,
	RouterModule,
	AppRoutingModule,
	Ng2SmartTableModule,
	ContextMenuModule.forRoot(),
	MatTableModule,
	MatCheckboxModule,
	MatPaginatorModule,
	MatSelectModule,

	AuthModule,
	HomeModule,
	LotModule,
],
providers: [],
bootstrap: [AppComponent]
})
export class AppModule {}
