import { BaseModel } from './base-model';
import { Status } from './status';

export class Store extends BaseModel {
	nameStore: number;
	statusStore: Status;
}
