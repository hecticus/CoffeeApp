import { BaseModel } from './base-model';


export class AuthUser extends BaseModel {
	username: string;
	email: string;
	password: string;
	archived: boolean;
	lastLogin: string;
}
