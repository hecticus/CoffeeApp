import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { TopbarComponent } from './core/topbar/topbar.component';
import { BreadcrumbsComponent } from './core/breadcrumbs/breadcrumbs.component';
import { FarmComponent } from 'src/app/components/farm/farm.component';
import { Resolution } from 'src/app/core/models/resolution';

@NgModule({
  declarations: [
    AppComponent,
    //  ImportComponets
    FarmComponent,

    // ImportCore
    SidebarComponent,
    TopbarComponent,
    BreadcrumbsComponent,
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
