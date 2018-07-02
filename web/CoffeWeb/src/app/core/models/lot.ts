import { Farm } from './farm';
import { BaseModel } from './base-model';

export class Lot extends BaseModel {
	nameLot: String;
	areaLot: String;
	heighLot: number;
	priceLot: number;
	statusLot: number;
	farm: Farm;
}
