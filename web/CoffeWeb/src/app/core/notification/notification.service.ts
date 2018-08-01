// import { NotificationsService } from 'angular4-notifications';
import { Injectable } from '@angular/core';


interface NotificationMessage {
	title: string;
	content: string;
}
@Injectable({
    providedIn: 'root'
})


export class NotificationService {
	public nsuccess: NotificationMessage = {title: '', content: ''};
	public nduplicate: NotificationMessage = {title: '', content: ''};
	public nerror: NotificationMessage = {title: '', content: ''};
	public nerror400: NotificationMessage = {title: '', content: ''};
	public nerror401: NotificationMessage = {title: '', content: ''};
	public nerror404: NotificationMessage = {title: '', content: ''};
	public nerror409: NotificationMessage = {title: '', content: ''};

	constructor(
		// private service: NotificationsService
	) { }

	// sucess() {
	// 	this.service.success(this.nsuccess.title, this.nsuccess.content);
	// }

	// sucessInsert(name?: string) {
	// 	this.service.success(this.nsuccess.title, this.nsuccess.content);
	// }

	// sucessUpdate(name?: string) {
	// 	this.service.success(this.nsuccess.title, this.nsuccess.content);
	// }

	// sucessDelete(name?: string) {
	// 	this.service.success(this.nsuccess.title, this.nsuccess.content);
	// }

	// errorDuplicated(name?: string) {
	// 	this.service.error(this.nduplicate.title, this.nduplicate.content);
	// 	// this.service.error('Registro duplicado', `El registro '${name}' ya existe en el sistema`);
	// }

	// error(error?: Response) {
	// 	switch (error.status) {
	// 		case 400:
	// 			this.service.error(this.nerror400.title, this.nerror400.content);
	// 			break;
	// 		case 401:
	// 			this.service.error(this.nerror401.title, this.nerror401.content);
	// 			break;
	// 		case 404:
	// 			this.service.error(this.nerror404.title, this.nerror404.content);
	// 			break;
	// 		case 409:
	// 			this.service.error(this.nerror409.title, this.nerror409.content);
	// 			break;
	// 		default:
	// 			this.service.error(this.nerror.title, this.nerror.content);
	// 			break;
	// 	}
	// }
}
