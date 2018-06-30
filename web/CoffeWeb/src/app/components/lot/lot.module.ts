import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LotListComponent } from './lot-list.component';
import { MatPaginatorModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
	imports: [
		BrowserModule,
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,

	],
	declarations: [
		LotListComponent,
	],
	exports: [
		LotListComponent,
	]
})
export class LotModule { }
