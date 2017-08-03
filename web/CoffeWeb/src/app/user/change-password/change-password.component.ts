import { Component, OnInit } from '@angular/core';
import { Password } from './password.interface';
import { BaseService } from '../../common/services/base.service';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { contentHeaders } from '../../common/headers';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent  extends BaseService implements OnInit{
  private passwordvalidator: Password;
  private urlUser: string= this.HOST+'/user';
  constructor(public router: Router, public http: Http) {
      super();
  }

  ngOnInit() {
    this.passwordvalidator = {
            password: '',
            confirmPassword: ''
    }
  }
  save(model: Password, isValid: boolean) {
        // call API to save customer
        console.log(model, isValid);
  }

  changePassword(model: Password, isValid: boolean)
  {
     if(isValid){
        event.preventDefault();
        let body = JSON.stringify({model});
        this.http.put(this.urlUser+'/reset', body,  { headers: contentHeaders })
          .subscribe(
              response => {
                if (response.json().message=="Sent")
                {
                    alert("Le fue enviado un correo");
                    this.router.navigate(['login']);
                }

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
      else{alert("claves no coinciden");}
  }
}
