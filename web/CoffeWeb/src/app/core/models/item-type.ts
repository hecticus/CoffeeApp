import { Unit } from './unit';
import { BaseModel } from './base-model';

export class ItemType extends BaseModel {
	nameItemType: string;
	unit: Unit;
}
