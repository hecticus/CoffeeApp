import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule }  from '@angular/forms';
import { HttpModule } from '@angular/http';

import { LogoutComponent } from './logout/logout.component';
import { LoginComponent } from './login/login.component';


@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    HttpModule,
  ],
  declarations: [
        LogoutComponent,
      //  UsertoolComponent,
        LoginComponent,        
    ]
})
export class UserModule { }
