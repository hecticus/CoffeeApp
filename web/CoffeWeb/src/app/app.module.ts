import { MatNativeDateModule, MatDatepickerModule } from '@angular/material';
import { HomeModule } from './components/home/home.module';
import { AuthModule } from './components/auth/auth.module';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ItemTypeComponent } from './components/item-type/item-type.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgModule } from '@angular/core';
import { ProviderTypeComponent } from './components/provider-type/provider-type.component';
import { RouterModule } from '@angular/router';
import { StoreComponent } from './components/store/store.component';
import { TableColumnDirective } from './core/table/tableColumnDirective';
import { TableComponent } from './core/table/table.component';
import { UnitComponent } from './components/unit/unit.component';
import { UtilsModule } from './core/utils/utils.module';
import { CommonModule } from '@angular/common';
import { ToastrModule } from 'ng6-toastr-notifications';
import { DatePickerModule } from './core/utils/custom-date-adapter.component';

@NgModule({
declarations: [
	AppComponent,
	// ImportCore

	TableComponent,
	TableColumnDirective,

	ProviderTypeComponent,
	ItemTypeComponent,
	StoreComponent,
	UnitComponent,

],
imports: [
	CommonModule,
	BrowserModule,
	BrowserAnimationsModule,
	HttpClientModule,
	FormsModule,
	RouterModule,
	Ng2SmartTableModule,
	DatePickerModule,
	ToastrModule.forRoot(),
	DatePickerModule,
	MatDatepickerModule,
	MatNativeDateModule,


	UtilsModule,
	AuthModule,
	HomeModule,
	AppRoutingModule,
],
providers: [],
bootstrap: [AppComponent]
})
export class AppModule {}
