import { homeRouting } from './../home/home.routes';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FarmComponent } from './farm.component';
import { BrowserModule } from '@angular/platform-browser';
import { FarmService } from './farm.service';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,

        homeRouting,
    ],
    declarations: [
        FarmComponent,
    ],
	exports: [
		FarmComponent,
    ],
    providers: [
		FarmService,
	]
})
export class FarmModule { }
