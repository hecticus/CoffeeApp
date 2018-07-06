import { InvoiceService } from './invoice.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import {
	ErrorStateMatcher,
	MatFormFieldModule,
	MatInputModule,
	MatPaginatorModule,
	MatSelectModule,
	ShowOnDirtyErrorStateMatcher
	} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { homeRouting } from '../home/home.routes';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { UtilsModule } from '../../core/utils/utils.module';


@NgModule({
	imports: [
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
		InvoiceComponent,
		InvoiceCreateComponent,
		InvoiceListComponent,
		InvoiceUpdateComponent,
		InvoiceReadComponent,
	],
	exports: [
		InvoiceComponent,
		InvoiceCreateComponent,
		InvoiceListComponent,
		InvoiceUpdateComponent,
		InvoiceReadComponent,
	],
	providers: [
		InvoiceService,
	]
})

export class InvoiceModule { }
