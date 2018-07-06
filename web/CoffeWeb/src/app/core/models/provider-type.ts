import { Provider } from './provider';
import { BaseModel } from './base-model';
import { ItemType } from './item-type';

export class ProviderType extends BaseModel {
	nameProviderType: string;
	providers: Provider[];
	itemTypes: ItemType[];
}
