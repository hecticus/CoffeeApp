import { BaseModel } from 'app/core/models/base.model';
import { MachineModel } from '../machineModel/machineModel';

export class OrderService extends BaseModel {
	name: string;
	workedTime: number;
	price: number;
	description: number;
	machineModels: MachineModel[] = new Array<MachineModel>();
}
