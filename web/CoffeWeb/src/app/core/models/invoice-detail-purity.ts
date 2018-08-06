import { Purity } from './purity';
import { InvoiceDetail } from './invoice-detail';
import { BaseModel } from './base-model';

export class InvoiceDetailPurity extends BaseModel {
	purity: Purity;
	invoiceDetail: InvoiceDetail;
	valueRateInvoiceDetailPurity: number;
	discountRatePurity: number;
	totalDiscountPurity: number;

}
