import { Farm } from './farm';
import { BaseModel } from './base-model';


export interface Lot extends BaseModel {
    idLot: number;
    farm: Farm;
    nameLot: String;
    areaLot: String;
    heighLot: number;
    priceLot: number;
    statusLot: number;
}