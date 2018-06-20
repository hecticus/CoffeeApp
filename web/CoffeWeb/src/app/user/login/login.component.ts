import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { IntervalObservable } from 'rxjs/observable/IntervalObservable';


import { contentHeaders } from '../../common/headers';
import { BaseService } from '../../common/services/base.service';
import { NotificationService } from '../../common/notification/notification.service';


const styles   = require('./login.component.css');
const template = require('./login.component.html');

@Component({
  selector: 'login',
  template: template,
  styles: [ styles ]
})
export class LoginComponent extends BaseService implements OnInit {

  private urlUser: string= this.HOST+'/user';
  constructor(
    private router: Router,
    private http: Http,
    private notificationService: NotificationService
  ) {

    super();

    /*this.http.get(this.urlUser+'/logout', { headers: contentHeaders })
       .subscribe(
        response => {
          if (response.json().message=="OK")
          {
            this.notificationService.genericsuccess("operación exitosa", "");
          }
        },
        error => {
          if(error.status===400)
          {
            this.notificationService.alert("verifique la direccion de correo");
          }
        }
       );
       sessionStorage.clear();
       sessionStorage.clear();
       localStorage.clear();
       contentHeaders.delete("Authorization");
       console.log("pase");*/
  }

  	ngOnInit() {
     }

  login(event, email, password) {
    console.log("login");
    this.router.navigate(['./home']);

        //console.log(this.urlUser);
    event.preventDefault();
    /*let body = JSON.stringify({ email, password });
    this.http.post(this.urlUser+'/login', body, { headers: contentHeaders })
       .subscribe(
        response => {
          if (response.json().result=="null")
          {
            alert(response.json().message);

          }
          console.log(response.json().result.token);
          localStorage.setItem('token', response.json().result.token);
          sessionStorage.setItem('token', response.json().result.token);
          contentHeaders.delete("Authorization");
          contentHeaders.append("Authorization", localStorage.getItem('token'));
          this.notificationService.sucessLogin();
          this.router.navigate(['home']);
        },
        error => {
                    let errorAux = error.json();
                    console.log(errorAux);
                    if(errorAux["error"]==400)
                    {
                        this.notificationService.error(errorAux["error"] === 400 ? 'Email o password es incorrecto' : 'Error del servidor');
                    }else
                    {
		      	            if(errorAux["error"]==935)	this.notificationService.alert(errorAux["message"])
			                  else this.notificationService.error(errorAux["message"])
                    }

      }
       );*/
  }

forgotPassword(event, emailM)
{
event.preventDefault();
    this.http.get(this.urlUser+'/reset/'+emailM, { headers: contentHeaders })
       .subscribe(
        response => {
          if (response.json().message=="Sent")
          {
            this.notificationService.genericsuccess("operación exitosa", "Le fue enviado un correo");
            setInterval(() => {location.reload();},3000);
          }
        },
        error => {
          if(error.status===400)
          {
            this.notificationService.alert("verifique la direccion de correo");
          }
        }
       );

}
  signup(event) {
    event.preventDefault();

  }
}
