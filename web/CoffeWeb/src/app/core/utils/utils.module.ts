import { ValidatorComponent } from './validator/validator.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


@NgModule({
	declarations: [
		ValidatorComponent,

	],
	imports: [
		BrowserModule,
		BrowserAnimationsModule

	],
	exports: [
		ValidatorComponent
	],
	providers: []
})
export class UtilsModule {}
