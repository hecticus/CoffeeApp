import { AuthRoutingModule } from './auth.routing';
import { UtilsModule } from '../../core/utils/utils.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatInputModule, MatFormFieldModule } from '@angular/material';
import { ChangePasswordComponent } from './change-password/change-password.component';

@NgModule({
	imports: [
		// BrowserModule,
		// BrowserAnimationsModule,
		HttpClientModule,
		FormsModule,
		RouterModule,
		CommonModule,
		ReactiveFormsModule,
		MatFormFieldModule,
		MatInputModule,

		UtilsModule,
		AuthRoutingModule,
	],
	declarations: [
		LoginComponent,
		ChangePasswordComponent,
	],
	exports: [
		LoginComponent,
		ChangePasswordComponent,
	],
})

export class AuthModule { }
