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
    public router: Router, 
    public http: Http, 
    private notificationService: NotificationService
  ) {
    
    super();
  }
  
  	ngOnInit() {
      localStorage.removeItem('token');
      sessionStorage.clear;
      localStorage.clear;
     }

  login(event, email, password) {
        console.log(this.urlUser);
    event.preventDefault();
    let body = JSON.stringify({ email, password });
    this.http.post(this.urlUser+'/login', body, { headers: contentHeaders })
       .subscribe(
        response => {
          if (response.json().result=="null")
          {
            alert(response.json().message);
            
          }
          localStorage.setItem('token', response.json().result.token);
          this.notificationService.sucessLogin();
          this.router.navigate(['home']);
        },
        error => {
          this.notificationService.error(error.status === 400 ? 'Email o password es incorrecto' : 'Error del servidor');
        }
       );
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
