import { ToastrModule } from 'ng6-toastr-notifications';
import { ValidatorComponent } from './validator/validator.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { SimpleNotificationsModule } from 'angular2-notifications';

@NgModule({
	declarations: [
		ValidatorComponent,
	],
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		SimpleNotificationsModule.forRoot(),
		ToastrModule.forRoot(),
	],
	exports: [
		ValidatorComponent,
	],
	providers: [
	]
})
export class UtilsModule {}
