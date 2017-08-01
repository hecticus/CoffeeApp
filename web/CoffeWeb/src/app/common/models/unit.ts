import { BaseModel } from './model.base';
import { Status } from './status';

export class Unit extends BaseModel
{
 	name: string;
    status: Status;
}