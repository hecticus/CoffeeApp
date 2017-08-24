import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from 'app/shared/shared.module';
import { LotCreateComponent } from './lot-create.component';
import { LotListComponent } from './lot-list.component';
import { LotReadComponent } from './lot-read.component';
import { LotUpdateComponent } from './lot-update.component';
import { LotComponent } from './lot.component';
import { homeRouting } from '../home/home.routes';
import { LotService } from './lot.service';


@NgModule({
  imports: [
    CommonModule,
    homeRouting,
    SharedModule
  ],
  declarations: [
    LotComponent,
    LotCreateComponent,
    LotListComponent,
    LotReadComponent,
    LotUpdateComponent
  
    ],
	providers: [
		LotService
	],
})
export class LotModule { }
