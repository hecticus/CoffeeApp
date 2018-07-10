import { CommonModule } from '@angular/common';
import {
	MatFormFieldModule,
	MatInputModule,
	MatPaginatorModule,
	MatSelectModule,
	} from '@angular/material';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { UtilsModule } from '../../core/utils/utils.module';
import { InvoiceDetailService } from './invoice-detail.service';
import { InvoiceComponent } from '../invoice/invoice.component';
import { InvoiceDetailListComponent } from './invoice-detail-list.component';


@NgModule({
	imports: [
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,

		UtilsModule,

	],
	declarations: [
		InvoiceComponent,
		InvoiceDetailListComponent,
	],
	exports: [
		InvoiceComponent,
		InvoiceDetailListComponent,
	],
	providers: [
		InvoiceDetailService,
	]
})

export class InvoiceDetailModule { }
