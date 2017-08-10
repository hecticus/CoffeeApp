import { Injectable } from '@angular/core';
import { NotificationsService, SimpleNotificationsComponent } from 'angular2-notifications';

@Injectable()
export class NotificationService {
    constructor(private service: NotificationsService) { }

    sucessInsert(name?: string) { this.createNotification(0, name); }
    sucessManyInsert(name?: string) { this.createNotification(5, name); }
    sucessLogin() { this.createNotification(10); }
    sucessUpdate(name?: string) { this.createNotification(1, name); }
    delete(name?: string) { this.createNotification(2, name); }
    deletes() { this.createNotification(3); }
    alert(name?: string) { this.createNotification(4, name); }
    error(name?: string) { this.createNotification(-1, name); }
    errorSystem() {this.createNotification(-2); }

    genericsuccess(title: string, body: string) { this.service.success(title, body); }
    genericerror(title: string, body: string) { this.service.error(title, body); }

    createNotification(p, name?: string) {
        var name = (name != undefined && name.length > 0) ? `\"${name}\"` : '';
        switch (p) {
            case 0:
                this.service.success("Creado Correctamente", `¡El registro ${name} ha sido creado`);
                break;
            case 1:
                this.service.success("Actualizado Correctamente", `¡El registro ${name} ha sido actualizado!`);
                break;
            case 2:
                this.service.success("Borrado Correctamente", `¡El registro ${name} ha sido borrado!`);
                break;
            case 3:
                this.service.success("Borrado Correctamente", `¡Los registros seleccionados han sido borrados!`);
                break;
            case 4:
                this.service.alert("Notificación", name);
                break;
            case 5:
                this.service.success("Creado Correctamente", `Los registros de ${name} han sido creados`);
                break;
            case 10:
                this.service.success("Login Exitoso", "Ha ingresado correctamente");
                break;
            case -1:
                this.service.error("Ocurrio el siguiente error", name);
                break;
            case -2:
                this.service.error('Error', 'Hubo un problema. Intentelo mas tarde');
                break;
        }
    }
}
