import { BaseModel } from './base-model';

export interface Farm extends BaseModel {
    idFarm: number;
    statusFarm: number;
    NameFarm: string;
}
