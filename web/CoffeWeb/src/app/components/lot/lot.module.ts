import { UtilsModule } from './../../core/utils/utils.module';
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
import { ValidatorComponent } from '../../core/utils/validator/validator.component';
import { LotReadComponent } from './lot-read.component';

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
		UtilsModule,

	],
	declarations: [
		LotComponent,
		LotListComponent,
		LotCreateComponent,
		LotReadComponent,
	],
	exports: [
		LotComponent,
		LotListComponent,
		LotCreateComponent,
		LotReadComponent,
	],
	providers: [
		LotService,
		{provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
	]
})
export class LotModule { }
