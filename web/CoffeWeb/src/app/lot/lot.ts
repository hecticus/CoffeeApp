import { BaseModel } from '../common/models/base.model';
import { Farm } from '../farm/farm';
import { CommonModule } from '@angular/common';


export class Lot extends BaseModel
{
    idLot: number;
    farm: Farm;
    price_lot:number;
    nameLot:string;
    statusLot: string;
    areaLot: number;
    heighLot: number;
}