import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import {
	ErrorStateMatcher,
	MatFormFieldModule,
	MatInputModule,
	MatPaginatorModule,
	MatSelectModule,
	ShowOnDirtyErrorStateMatcher
	} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LotComponent } from './lot.component';
import { LotCreateComponent } from './lot-create.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotRoutingModule } from './lot.routing';
import { LotService } from './lot.service';
import { LotUpdateComponent } from './lot-update.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { UtilsModule } from '../../core/utils/utils.module';

@NgModule({
	imports: [
		FormsModule,
		ReactiveFormsModule,
		// BrowserAnimationsModule,
		CommonModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,

		UtilsModule,
		LotRoutingModule,

	],
	declarations: [
		LotComponent,
		LotListComponent,
		LotCreateComponent,
		LotReadComponent,
		LotUpdateComponent,
	],
	exports: [
		LotComponent,
		LotListComponent,
		LotCreateComponent,
		LotReadComponent,
		LotUpdateComponent,
	],
	providers: [
		LotService,
	]
})
export class LotModule { }
