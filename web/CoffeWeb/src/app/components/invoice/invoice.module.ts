import { PurchaseCreateComponent } from './purchase-created.component';
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
	MatRadioModule,
	} from '@angular/material';
	import { MyDatePickerModule } from 'mydatepicker';
import { ModalModule } from 'ngx-bootstrap/modal';
import { HarvestCreateComponent } from './harvest-create.component';

@NgModule({
	imports: [
		FormsModule,
		ReactiveFormsModule,
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
		MatRadioModule,
		ModalModule.forRoot(),
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
		HarvestCreateComponent,
		PurchaseCreateComponent
	],
	exports: [
		InvoiceComponent,
		InvoiceCreateComponent,
		InvoiceListComponent,
		InvoiceUpdateComponent,
		InvoiceReadComponent,
		HarvestCreateComponent,
		PurchaseCreateComponent
	],
	providers: [
		InvoiceService,
		FilterService,
		ExcelService,
	]
})


export class InvoiceModule { }
