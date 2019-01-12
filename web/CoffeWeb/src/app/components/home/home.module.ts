import { InvoiceDetailModule } from './../invoice-detail/invoice-detail.module';
import { PurityModule } from './../purity/purity.module';
import { StoreModule } from './../store/store.module';
import { ItemTypeModule } from './../item-type/item-type.module';
import { InvoiceModule } from '../invoice/invoice.module';
import { ProviderModule } from '../provider/provider.module';
import { HomeRoutingModule } from './home.routing';
import { UtilsModule } from '../../core/utils/utils.module';
import { HomeComponent } from './home.component';
import { BreadcrumbsComponent } from '../../core/breadcrumbs/breadcrumbs.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { ErrorStateMatcher, ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { SidebarComponent } from '../../core/sidebar/sidebar.component';
import { TopbarComponent } from '../../core/topbar/topbar.component';
import { LotModule } from '../lot/lot.module';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		UtilsModule,
		InvoiceModule,
		ProviderModule,
		LotModule,
		ItemTypeModule,
		StoreModule,
		PurityModule,
		HomeRoutingModule,
		InvoiceDetailModule
	],
	declarations: [
		HomeComponent,
		SidebarComponent,
		TopbarComponent,
		BreadcrumbsComponent,
	],
	exports: [
		SidebarComponent,
		TopbarComponent,
		BreadcrumbsComponent,
	],
	providers: [
		{provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
	]
})
export class HomeModule { }
