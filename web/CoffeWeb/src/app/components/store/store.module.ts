import { StoreComponent } from './store.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreService } from './store.service';

@NgModule({
	imports: [
		CommonModule
	],
	declarations: [
		StoreComponent
	],
	exports: [
		StoreComponent
	],
	providers: [
		StoreService
	]

})


export class StoreModule { }
