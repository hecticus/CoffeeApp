import { FormsModule, NgModel } from '@angular/forms';

import { AppComponent } from './app.component';
import { BreadcrumbsComponent } from './core/breadcrumbs/breadcrumbs.component';
import { BrowserModule } from '@angular/platform-browser';
import { FarmComponent } from 'src/app/components/farm/farm.component';
import { HttpClientModule } from '@angular/common/http';
import { LotComponent } from './components/lot/lot.component';
import { NgModule } from '@angular/core';
import { ProofModule } from './proof/proof.module';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { TopbarComponent } from './core/topbar/topbar.component';

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
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ProofModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }

