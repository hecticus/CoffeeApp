import { BaseModel } from './model.base';
import { Status } from './status';

export class Farm extends BaseModel
{
 	name: string;
    status: Status;
}