import { CosechadorCreateComponent } from './cosechador-create.component';
import { VendedorCreateComponent } from './vendedor-create.component';
import { CommonModule } from '@angular/common';
import { CosechadorUpdateComponent } from './cosechador-update.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
	MatCheckboxModule,
	MatFormFieldModule,
	MatInputModule,
	MatPaginatorModule,
	MatSelectModule,
	MatTableModule
	} from '@angular/material';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgModule } from '@angular/core';
import { ProviderComponent } from './provider.component';
import { ProviderCreateComponent } from './provider-create.component';
import { ProviderListComponent } from './provider-list.component';
import { ProviderReadComponent } from './provider-read.component';
import { ProviderRoutingModule } from './provider.routing';
import { ProviderService } from './provider.service';
import { ProviderUpdateComponent } from './provider-update.component';
import { UtilsModule } from '../../core/utils/utils.module';
import { VendedorUpdateComponent } from './vendedor-update.component';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		// BrowserAnimationsModule,
		MatTableModule,
		MatCheckboxModule,
		MatPaginatorModule,
		MatSelectModule,
		MatFormFieldModule,
		MatInputModule,
		ModalModule.forRoot(),

		UtilsModule,
		ProviderRoutingModule,
	],
	declarations: [
		ProviderComponent,
		ProviderListComponent,
		ProviderCreateComponent,
		ProviderReadComponent,
		ProviderUpdateComponent,
		VendedorUpdateComponent,
		CosechadorUpdateComponent,
		CosechadorCreateComponent,
		VendedorCreateComponent,
	],
	exports: [
		ProviderComponent,
		ProviderListComponent,
		ProviderCreateComponent,
		ProviderReadComponent,
		ProviderUpdateComponent,
		VendedorUpdateComponent,
		CosechadorUpdateComponent,
		CosechadorCreateComponent,
		VendedorCreateComponent
	],
	providers: [
		ProviderService,
	]

})
export class ProviderModule { }
