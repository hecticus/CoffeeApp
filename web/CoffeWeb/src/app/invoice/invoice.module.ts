import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvoiceComponent } from './invoice.component';
import { InvoiceListComponent } from './invoice-list.component';

import { SharedModule } from '../shared/shared.module';
import { homeRouting } from '../home/home.routes';

import { InvoiceService } from './invoice.service';

//-drocha-
import { MyDatePickerModule } from 'mydatepicker';
import { BrowserModule } from '@angular/platform-browser';
//-drocha-

@NgModule({
    imports: [
    CommonModule,
    homeRouting,
    SharedModule,
  //-drocha-
   MyDatePickerModule,
   BrowserModule
  //-drocha-
  ],
  declarations: [
    InvoiceComponent,
    InvoiceListComponent
    ],
	providers: [
		InvoiceService
	],
})
export class InvoiceModule { }
