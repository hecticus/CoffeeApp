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
import { ProviderUpdateComponent } from './provider-update.componet';
import { ProviderListComponent } from './provider-list.component';
import { ProviderCreateComponent } from './provider-create.component';
import { ProviderReadComponent } from './provider-read.component';

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
		ProviderCreateComponent,
		ProviderReadComponent,
		ProviderUpdateComponent,
	],
	exports: [
		ProviderComponent,
		ProviderListComponent,
		ProviderCreateComponent,
		ProviderReadComponent,
		ProviderUpdateComponent,
	],
	providers: [
		ProviderService,
	]

})
export class ProviderModule { }
