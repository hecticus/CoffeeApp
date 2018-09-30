import { Provider } from './provider';
import { BaseModel } from './base-model';
import { Status } from './status';
import { InvoiceDetail } from './invoice-detail';

export class Invoice extends BaseModel {
	provider: Provider;
	statusInvoice: Status;
	closedDateInvoice: string;
	totalInvoice: number;
	invoiceDetails: InvoiceDetail [];
	startDate: string;
	closedDate: string;
}
