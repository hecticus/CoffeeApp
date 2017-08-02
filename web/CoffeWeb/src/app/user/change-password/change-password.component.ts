import { Component, OnInit } from '@angular/core';
import { Password } from './password.interface';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

private passwordvalidator: Password;

  constructor() { }

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

}
