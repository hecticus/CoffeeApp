import { LotCreateComponent } from './lot-create.component';
import { homeRouting } from './../home/home.routes';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LotListComponent } from './lot-list.component';
import { MatPaginatorModule, MatSelectModule,
	MatFormFieldModule, MatInputModule, ErrorStateMatcher,
	ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LotComponent } from './lot.component';
import { LotService } from './lot.service';

@NgModule({
	imports: [
		BrowserModule,
		FormsModule,
		ReactiveFormsModule,
		BrowserAnimationsModule,
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,

		homeRouting,

	],
	declarations: [
		LotComponent,
		LotListComponent,
		LotCreateComponent,
	],
	exports: [
		LotComponent,
		LotListComponent,
		LotCreateComponent,
	],
	providers: [
		LotService,
		{provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
	]
})
export class LotModule { }
