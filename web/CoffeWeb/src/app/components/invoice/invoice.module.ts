import { InvoiceRoutingModule } from './invoice.routing';
import { InvoiceService } from './invoice.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import {
	MatFormFieldModule,
	MatInputModule,
	MatPaginatorModule,
	MatSelectModule,
	} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { UtilsModule } from '../../core/utils/utils.module';
import { InvoiceDetailListComponent } from '../invoice-detail/invoice-detail-list.component';
import { InvoiceDetailModule } from '../invoice-detail/invoice-detail.module';


@NgModule({
	imports: [
		FormsModule,
		ReactiveFormsModule,
		// BrowserAnimationsModule,
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,

		UtilsModule,
		InvoiceRoutingModule,
		InvoiceDetailModule,

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
