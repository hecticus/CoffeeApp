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

	constructor(
		public toastr: ToastrManager
	) { }

	showSuccess() {
		this.toastr.successToastr('Exitoso', 'Operacion Exitosa!');
	}

	showError() {
		this.toastr.errorToastr('Error', 'Error en el sistema, chequee con el administrador');
	}

	showWarning() {
		this.toastr.warningToastr('Alert', 'Error en el sistema, chequee con el administrador');
	}

	showInfo(name: string) {
		this.toastr.infoToastr('Importante', name );
	}

	sucessInsert(name: string) {
		this.toastr.successToastr('Exitoso', 'Creación exitosa de ' + name);
	}

	sucessUpdate(name: string) {
		this.toastr.successToastr('Exitoso', 'Actualización exitosa de ' + name);
	}

	sucessDelete(name: string) {
		this.toastr.successToastr('Exitoso', 'Eliminacion exitosa de ' + name);
	}

	errorDuplicated(name: string) {
		this.toastr.errorToastr('Duplicado', 'Registro Duplicado');
	}

	error(error?: Response) {
		switch (error.status) {
			case 400:
				this.toastr.errorToastr('Solicitud Incorrecta', 'Dato Invalido');
				break;
			case 401:
				this.toastr.errorToastr('No Autorizado', 'Credenciales inválidas');
				break;
			case 404:
				this.toastr.errorToastr('No Encontrado', 'Recurso no encontrado');
				break;
			case 409:
				this.toastr.errorToastr('Conflicto', 'Chequee dependencias');
				break;
			case 500:
				this.toastr.warningToastr('Alert', 'Error en el sistema, chequee con el administrador');
				break;
			default:
				this.toastr.errorToastr('Conflicto', 'Chequee dependencias');
				break;
		}
	}
}

