import { ExcelService } from './../../core/utils/excel/excel.service';
import { CommonModule } from '@angular/common';
import { FilterService } from './../../core/utils/filter/filter.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InvoiceComponent } from './invoice.component';
import { InvoiceCreateComponent } from './invoice-create.component';
import { InvoiceDetailModule } from '../invoice-detail/invoice-detail.module';
import { InvoiceListComponent } from './invoice-list.component';
import { InvoiceReadComponent } from './invoice-read.component';
import { InvoiceRoutingModule } from './invoice.routing';
import { InvoiceService } from './invoice.service';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { UtilsModule } from '../../core/utils/utils.module';
import {
	MatFormFieldModule,
	MatInputModule,
	MatPaginatorModule,
	MatSelectModule,
	MatDatepickerModule,
	MatNativeDateModule,
	MatIconModule,
	} from '@angular/material';
	import { MyDatePickerModule } from 'mydatepicker';

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
		MatDatepickerModule,
		MatNativeDateModule,
		MatIconModule,
		MyDatePickerModule,

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
		FilterService,
		ExcelService,
	]
})


export class InvoiceModule { }
