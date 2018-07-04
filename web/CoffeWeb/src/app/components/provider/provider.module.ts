import { ProviderListComponent } from './provider-list.component';
import { ProviderService } from './provider.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProviderComponent } from './provider.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule, MatCheckboxModule,
		MatPaginatorModule, MatSelectModule,
		MatFormFieldModule, MatInputModule } from '@angular/material';
import { homeRouting } from '../home/home.routes';
import { UtilsModule } from '../../core/utils/utils.module';

@NgModule({
	imports: [
		CommonModule,
		BrowserModule,
		FormsModule,
		ReactiveFormsModule,
		BrowserAnimationsModule,
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,

		homeRouting,
		UtilsModule,
	],
	declarations: [
		ProviderComponent,
		ProviderListComponent,
	],
	exports: [
		ProviderComponent,
		ProviderListComponent,
	],
	providers: [
		ProviderService,
	]

})
export class ProviderModule { }
