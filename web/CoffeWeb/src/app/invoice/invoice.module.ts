import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvoiceComponent } from './invoice.component';
import { InvoiceListComponent } from './invoice-list.component';

import { SharedModule } from '../shared/shared.module';
import { homeRouting } from '../home/home.routes';

import { InvoiceService } from './invoice.service';


@NgModule({
    imports: [
    CommonModule,
    homeRouting,
    SharedModule
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
