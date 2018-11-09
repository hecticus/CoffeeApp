import { InvoiceDetailPurity } from './invoice-detail-purity';
import { Store } from './store';
import { Lot } from './lot';
import { ItemType } from './item-type';
import { BaseModel } from './base-model';
import { Invoice } from './invoice';

export class InvoiceDetail extends BaseModel {
	invoice: Invoice;
	itemType: ItemType;
	lot: Lot;
	store: Store;
	priceItemTypeByLot: number;
	costItemType: number;
	amountInvoiceDetail: number;
	nameReceived: number;
	nameDelivered: number;
	note: string;
	statusInvoiceDetail: string;
	invoiceDetailPurity: InvoiceDetailPurity[];
	startDate: String;
	closedDate: string;



}
