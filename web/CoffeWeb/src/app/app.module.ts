import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { TopbarComponent } from './core/topbar/topbar.component';
import { BreadcrumbsComponent } from './core/breadcrumbs/breadcrumbs.component';
import { FarmComponent } from 'src/app/components/farm/farm.component';
import { HttpClientModule } from '@angular/common/http';
import { LotComponent } from './components/lot/lot.component';
import { HeroesComponent } from './components/heroes/heroes.component';
import { FormsModule } from '@angular/forms';
import { HeroesDetailComponent } from './components/heroes-detail/heroes-detail.component';
import { MessagesComponent } from './components/messages/messages.component';

@NgModule ({
  declarations: [
    AppComponent,
    //  ImportComponets
    FarmComponent,

    // ImportCore
    SidebarComponent,
    TopbarComponent,
    BreadcrumbsComponent,
    LotComponent,
    HeroesComponent,
    HeroesDetailComponent,
    MessagesComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

