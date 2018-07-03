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


@NgModule({
declarations: [
	AppComponent,
	//  ImportComponets
	FarmComponent,
	// ImportCore
	HeroesComponent,
	HeroesDetailComponent,

	MessagesComponent,
	DashboardComponent,
	TableComponent,
	TableColumnDirective,
	NotificationComponent,
	StatusComponent,

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
