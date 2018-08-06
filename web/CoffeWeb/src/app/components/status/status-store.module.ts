import { StatusStoreService } from './status-store.service';
import { StatusLotService } from './status-lot.service';
import { StatusInvoiceDetailService } from './status-invoice-detail.service';
import { StatusFarmService } from './status-farm.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatusComponent } from './status.component';
import { StatusInvoiceService } from './status-invoice.service';
import { StatusProviderService } from './status-provider.service';

@NgModule({
	imports: [
		CommonModule
	],
	declarations: [
		StatusComponent,
	],
	exports: [
		StatusComponent,
	],
	providers: [
		StatusFarmService,
		StatusInvoiceService,
		StatusInvoiceDetailService,
		StatusLotService,
		StatusProviderService,
		StatusStoreService,
	]
})
export class StatusStoreModule { }
