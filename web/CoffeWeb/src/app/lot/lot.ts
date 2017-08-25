import { BaseModel } from '../common/models/base.model';
import { Farm } from '../farm/farm';


export class Lot extends BaseModel
{
    farm: Farm;
    price_lot:number;
    nameLot:string;

}