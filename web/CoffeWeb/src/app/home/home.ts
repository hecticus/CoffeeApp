import { Component } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';
import { AuthHttp } from 'angular2-jwt';
import { NotificationService } from '../common/notification/notification.service';

const styles = require('./home.css');
const template = require('./home.html');


@Component({
  selector: 'home',
  template: template,
  styles: [ styles ]
})
export class Home {
  jwt: string;
  decodedJwt: string;
  response: string;
  api: string;

  constructor(public router: Router,
   public http: Http,
   public authHttp: AuthHttp, 
   private notificationService: NotificationService) {
    this.jwt = localStorage.getItem('token');
  //  this.decodedJwt = this.jwt && window.jwt_decode(this.jwt);
}

 openNav() {
    document.getElementById("menuHome").style.width = "250px";
    //document.getElementById("home").style.marginLeft = "250px";
}

closeNav() {
    document.getElementById("menuHome").style.width = "0";
    //document.getElementById("home").style.marginLeft= "0";
}

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  callSecuredApi() {
    this._callApi('Secured', 'http://localhost:3001/api/protected/random-quote');
  }

  _callApi(type, url) {
    this.response = null;
    if (type === 'Anonymous') {
      // For non-protected routes, just use Http
      this.http.get(url)
        .subscribe(
          response => this.response = response.text(),
          error => this.response = error.text()
        );
    }
    if (type === 'Secured') {
      // For protected routes, use AuthHttp
      this.authHttp.get(url)
        .subscribe(
          response => this.response = response.text(),
          error => this.response = error.text()
        );
    }
  }
}
