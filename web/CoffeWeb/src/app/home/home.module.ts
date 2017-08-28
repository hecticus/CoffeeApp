import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule }  from '@angular/forms';
import { HttpModule } from '@angular/http';
import { Router } from '@angular/router';

import { homeRouting} from './home.routes';

import { MenuComponent } from '../common/menu/menu.component';
import { MenuItemComponent } from '../common/menu/menu-item/menu-item.component';
import { HomeComponent } from './home.component';

import { LotModule } from '../lot/lot.module';

import { SharedModule } from '../shared/shared.module';

@NgModule({ 
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        FormsModule,
        HttpModule,

        homeRouting,

        SharedModule,

        LotModule
        
        ],
    declarations: [
        MenuComponent,
        MenuItemComponent,        
        HomeComponent      
        ],
    })
export class HomeModule { }