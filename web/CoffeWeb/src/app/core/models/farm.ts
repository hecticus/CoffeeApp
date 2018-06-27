import { BaseModel } from './base-model';

export interface Farm extends BaseModel {
    NameFarm: string;
    statusFarm?: string;
}
