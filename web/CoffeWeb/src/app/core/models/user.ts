import { BaseModel } from './base-model';
import { AuthUser } from './authUser';

import { Multimedia } from './Multimedia';
import { Contact } from './contact';

export class User extends BaseModel {
	authUser: AuthUser;
	firstName: string;
	lastName: string;
	fullName: string;
	description: string;
	// contact: Contact = new Contact();
	contact: Contact;
	mediaPhoto: Multimedia;
}
