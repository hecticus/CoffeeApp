import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule }  from '@angular/forms';
import { HttpModule } from '@angular/http';

import { homeRouting } from './home.routes';

import { BreadcrumbsComponent } from '../common/breadcrumbs/breadcrumbs.component';
//import { UsertoolComponent } from '../common/usertool/usertool.component';
import { MenuComponent } from '../common/menu/menu.component';
import { MenuItemComponent } from '../common/menu/menu-item/menu-item.component';
import { HomeComponent } from './home.component';

@NgModule({ 
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        FormsModule,
        HttpModule,
        homeRouting
    ],
    declarations: [
        BreadcrumbsComponent,
      //  UsertoolComponent,
        MenuComponent,
        MenuItemComponent,        
        HomeComponent,        
    ]
})
export class HomeModule { }
