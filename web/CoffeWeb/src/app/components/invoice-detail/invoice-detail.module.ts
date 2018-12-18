import { InvoiceDetailRoutingModule } from './invoice-detail..routing';
import { InvoiceDetailComponent } from './invoice-detail.component';
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
import { InvoiceDetailListComponent } from './invoice-detail-list.component';
import { InvoiceDetailReadComponent } from './invoice-detail-read.component';
import { ModalModule } from 'ngx-bootstrap/modal';


@NgModule({
	imports: [
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,
		ModalModule.forRoot(),

		UtilsModule,
		InvoiceDetailRoutingModule,

	],
	declarations: [
		InvoiceDetailComponent,
		InvoiceDetailListComponent,
		InvoiceDetailReadComponent,
	],
	exports: [
		InvoiceDetailComponent,
		InvoiceDetailListComponent,
		InvoiceDetailReadComponent,
	],
	providers: [
		InvoiceDetailService,
	]
})

export class InvoiceDetailModule { }
