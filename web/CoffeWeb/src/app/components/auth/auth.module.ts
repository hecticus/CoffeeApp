import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ValidatorComponent } from '../../core/utils/validator/validator.component';

@NgModule({
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		HttpClientModule,
		FormsModule,
		RouterModule,
		CommonModule
	],
	declarations: [
		LoginComponent,
		ValidatorComponent,
	],
	exports: [
		LoginComponent,
		ValidatorComponent,
	],
})

export class AuthModule { }
