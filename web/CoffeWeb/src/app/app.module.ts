import { UtilsModule } from './core/utils/utils.module';
import { AppComponent } from './app.component';
import { AuthModule } from './components/auth/auth.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FarmComponent } from 'src/app/components/farm/farm.component';
import { FormsModule } from '@angular/forms';
import { HeroesComponent } from './components/heroes/heroes.component';
import { HeroesDetailComponent } from './components/heroes-detail/heroes-detail.component';
import { HomeModule } from './components/home/home.module';
import { HttpClientModule } from '@angular/common/http';
import { LotModule } from './components/lot/lot.module';
import { MessagesComponent } from './components/messages/messages.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgModule } from '@angular/core';
import { RouterModule} from '@angular/router';
import { routing } from './app.routes';
import { TableColumnDirective } from './core/table/tableColumnDirective';
import { TableComponent } from './core/table/table.component';
import { NotificationComponent } from './core/notification/notification.component';
import { StatusComponent } from './components/status/status.component';
import { ProviderComponent } from './components/provider/provider.component';
import { InvoiceComponent } from './components/invoice/invoice.component';
import { InvoiceDetailComponent } from './components/invoice-detail/invoice-detail.component';
import { ProviderTypeComponent } from './components/provider-type/provider-type.component';
import { ItemTypeComponent } from './components/item-type/item-type.component';
import { StoreComponent } from './components/store/store.component';
import { UnitComponent } from './components/unit/unit.component';


@NgModule({
declarations: [
	AppComponent,
	// ImportCore
	HeroesComponent,
	HeroesDetailComponent,

	MessagesComponent,
	DashboardComponent,
	TableComponent,
	TableColumnDirective,
	NotificationComponent,
	StatusComponent,
	InvoiceComponent,
	InvoiceDetailComponent,
	ProviderTypeComponent,
	ItemTypeComponent,
	StoreComponent,
	UnitComponent,

],
imports: [
	BrowserModule,
	BrowserAnimationsModule,
	HttpClientModule,
	FormsModule,
	RouterModule,
	Ng2SmartTableModule,

	routing,
	AuthModule,
	HomeModule,
	LotModule,
	UtilsModule,
],
providers: [],
bootstrap: [AppComponent]
})
export class AppModule {}
