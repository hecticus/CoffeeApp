import { Status } from './status';
import { Farm } from './farm';
import { BaseModel } from './base-model';

export class Lot extends BaseModel {
	nameLot: string;
	areaLot: string;
	heighLot: number;
	priceLot: number;
	statusLot: Status;
	farm: Farm;
}
