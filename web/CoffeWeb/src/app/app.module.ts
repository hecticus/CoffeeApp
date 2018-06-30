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
import { MatPaginatorModule } from '@angular/material';
import { MatTableModule } from '@angular/material/table';
import { MessagesComponent } from './components/messages/messages.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { TableColumnDirective } from './core/table/tableColumnDirective';
import { TableComponent } from './core/table/table.component';
import { TopbarComponent } from './core/topbar/topbar.component';

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
	MatCheckboxModule,
	MatPaginatorModule,
],
providers: [],
bootstrap: [AppComponent]
})
export class AppModule {}
