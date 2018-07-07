import { UtilsModule } from '../../core/utils/utils.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ValidatorComponent } from '../../core/utils/validator/validator.component';
import { MatInputModule, MatFormFieldModule } from '../../../../node_modules/@angular/material';

@NgModule({
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		HttpClientModule,
		FormsModule,
		RouterModule,
		CommonModule,
		ReactiveFormsModule,
		MatFormFieldModule,
		MatInputModule,

		UtilsModule,
	],
	declarations: [
		LoginComponent,
	],
	exports: [
		LoginComponent,
	],
})

export class AuthModule { }
