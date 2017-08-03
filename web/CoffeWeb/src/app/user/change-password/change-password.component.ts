import { Component, OnInit } from '@angular/core';
import { Password } from './password.interface';
import { BaseService } from '../../common/services/base.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
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
  private accessToken: string;	
	private email: string;
   
  constructor(public router: Router, public http: Http, private route: ActivatedRoute) {
      super();
  }

  ngOnInit() {
    this.passwordvalidator = {
            password: '',
            confirmPassword: ''
    };
    this.getFromUrl();
		if (this.accessToken !== undefined) { }
  }
  save(model: Password, isValid: boolean) {
        // call API to save customer
        console.log(model, isValid);
  }

  changePassword(model: Password, isValid: boolean)
  {
     if(isValid){
       contentHeaders.append('Authorization',this.accessToken);
        event.preventDefault();
        let password = model['password'];
        let email = this.email;
        let body = JSON.stringify({password,email});
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
      else{
        alert("claves no coinciden");
      }
  }

 
	getFromUrl() {
		this.route.params.subscribe((params: Params) =>  {
      this.accessToken = params['token'];
    	this.email = params['to_email'];
     console.log(this.accessToken);
     console.log(this.email );
   });
	}
}