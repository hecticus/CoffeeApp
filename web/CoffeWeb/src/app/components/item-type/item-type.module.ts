import { ItemTypeService } from './item-type.service';
import { ItemTypeComponent } from './item-type.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
imports: [
	CommonModule
	],
	declarations: [
		ItemTypeComponent,
	],
	providers: [
		ItemTypeService,
	]
})
export class ItemTypeModule { }
