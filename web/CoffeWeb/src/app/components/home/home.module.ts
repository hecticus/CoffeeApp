import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule, MatSelectModule,
	MatFormFieldModule, MatInputModule, ErrorStateMatcher,
	ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LotListComponent } from '../lot/lot-list.component';
import { LotModule } from '../lot/lot.module';
import { AuthModule } from '../auth/auth.module';

@NgModule({
    imports: [
        CommonModule,
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

		LotModule,
		AuthModule,

    ],
	declarations: [
		LotListComponent,
		AuthModule,
	],
	exports: [
		LotListComponent,
	],
	providers: [
		{provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
	]
})
export class HomeModule { }
