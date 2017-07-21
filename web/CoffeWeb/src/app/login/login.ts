import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { contentHeaders } from '../common/headers';
import { BaseService } from '../common/services/base.service';

const styles   = require('./login.css');
const template = require('./login.html');

@Component({
  selector: 'login',
  template: template,
  styles: [ styles ]
})
export class Login  extends BaseService {
      private urlUser: string = this.HOST + '/user'
  constructor(public router: Router, public http: Http)
   {
     super();
  }

  login(event, email, password) {
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
          this.router.navigate(['home']);
        },
        error => {
          if(error.status===400)
          {
            alert(error.json().message);
          }
          console.log(error.json().message);
        }
      );
  }

  signup(event) {
    event.preventDefault();
    this.router.navigate(['signup']);
  }
}
