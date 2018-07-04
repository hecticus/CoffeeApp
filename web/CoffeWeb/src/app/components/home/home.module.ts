import { ProviderModule } from './../provider/provider.module';
import { homeRouting } from './home.routes';
import { HomeComponent } from './home.component';
import { AuthModule } from '../auth/auth.module';
import { BreadcrumbsComponent } from '../../core/breadcrumbs/breadcrumbs.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ErrorStateMatcher, ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LotModule } from '../lot/lot.module';
import { NgModule } from '@angular/core';
import { SidebarComponent } from '../../core/sidebar/sidebar.component';
import { TopbarComponent } from '../../core/topbar/topbar.component';

@NgModule({
	imports: [
		CommonModule,
		BrowserModule,
		FormsModule,
		ReactiveFormsModule,
		BrowserAnimationsModule,

		homeRouting,
		LotModule,
		AuthModule,
		ProviderModule,

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
