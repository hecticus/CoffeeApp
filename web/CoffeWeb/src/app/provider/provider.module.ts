import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProviderComponent } from './provider.component';
import { ProviderListComponent } from './provider-list.component';

import { SharedModule } from '../shared/shared.module';
import { homeRouting } from '../home/home.routes';
import { ProviderCreateComponent } from './provider-create.component';
import { ProviderUpdateComponent } from './provider-update.component';
import { ProviderReadComponent } from './provider-read.component';

import { ProviderService } from './provider.service';

@NgModule({
    imports: [
    CommonModule,
    homeRouting,
    SharedModule
  ],
  declarations: [
    ProviderComponent,
    ProviderListComponent,
    ProviderCreateComponent,
    ProviderUpdateComponent,
    ProviderReadComponent
    ],
	providers: [
		ProviderService
	],
})
export class ProviderModule { }
