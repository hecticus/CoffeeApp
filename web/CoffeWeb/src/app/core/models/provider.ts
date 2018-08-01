import { BaseModel } from './base-model';
import { ProviderType } from './provider-type';
import { Status } from './status';
import { Invoice } from './invoice';

export class Provider extends BaseModel {
	providerType: ProviderType;
	nitProvider: string;
	nameProvider: string;
	addressProvider: string ;
	numberProvider: string;
	emailProvider: string;
	photoProvider: string;
	contactNameProvider: string;
	statusProvider: Status;
	// invoices: Invoice[];
}
