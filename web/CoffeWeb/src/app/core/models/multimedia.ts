import { BaseModel } from './base-model';
import { MultimediaCDN } from './multimediaCDN';

export class Multimedia extends BaseModel {
	dtype: string;
	name: string;
	description: string;
	multimediaCDN: MultimediaCDN;
}
