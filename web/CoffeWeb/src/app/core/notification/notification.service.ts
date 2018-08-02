import { ToastrManager } from 'ng6-toastr-notifications';
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
	public ninfo: NotificationMessage = {title: '', content: ''};
	public nerror: NotificationMessage = {title: '', content: ''};
	public nerror400: NotificationMessage = {title: '', content: ''};
	public nerror401: NotificationMessage = {title: '', content: ''};
	public nerror404: NotificationMessage = {title: '', content: ''};
	public nerror409: NotificationMessage = {title: '', content: ''};

	constructor(
		public toastr: ToastrManager
	) { }

	showSuccess() {
		this.toastr.successToastr(this.nsuccess.title, this.nsuccess.content);
	}

	showError() {
		this.toastr.errorToastr('This is error toast.', 'Oops!');
	}

	showWarning() {
		this.toastr.warningToastr('This is warning toast.', 'Alert!');
	}

	showInfo() {
		this.toastr.infoToastr(this.ninfo.title, this.ninfo.content);
	}
	// sucess() {
	// 	this.service.success(this.nsuccess.title, this.nsuccess.content);
	// }

	sucessInsert(name?: string) {
		this.toastr.successToastr(this.nsuccess.title, this.nsuccess.content);
	}

	sucessUpdate(name?: string) {
		this.toastr.successToastr(this.nsuccess.title, this.nsuccess.content);
	}

	sucessDelete(name?: string) {
		this.toastr.successToastr(this.nsuccess.title, this.nsuccess.content);
	}

	errorDuplicated(name?: string) {
		this.toastr.errorToastr(this.nduplicate.title, this.nduplicate.content);
		// this.service.error('Registro duplicado', `El registro '${name}' ya existe en el sistema`);
	}

	error(error?: Response) {
		switch (error.status) {
			case 400:
			this.toastr.errorToastr(this.nerror400.title, this.nerror400.content);
				break;
			case 401:
				this.toastr.errorToastr(this.nerror401.title, this.nerror401.content);
				break;
			case 404:
				this.toastr.errorToastr(this.nerror404.title, this.nerror404.content);
				break;
			case 409:
				this.toastr.errorToastr(this.nerror409.title, this.nerror409.content);
				break;
			default:
				this.toastr.errorToastr(this.nerror.title, this.nerror.content);
				break;
		}
	}
}
