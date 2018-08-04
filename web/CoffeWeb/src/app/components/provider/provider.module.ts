import { CosechadorComponent } from './cosechador.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ProviderService } from './provider.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProviderComponent } from './provider.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule, MatCheckboxModule,
		MatPaginatorModule, MatSelectModule,
		MatFormFieldModule, MatInputModule } from '@angular/material';
import { UtilsModule } from '../../core/utils/utils.module';
import { ProviderUpdateComponent } from './provider-update.component';
import { ProviderListComponent } from './provider-list.component';
import { ProviderCreateComponent } from './provider-create.component';
import { ProviderReadComponent } from './provider-read.component';
import { ProviderRoutingModule } from './provider.routing';
import { VendedorComponent } from './vendedor.component';
import { VendedorUpdateComponent } from './vendedor-update.component';
import { CosechadorUpdateComponent } from './cosechador-update.component';

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
		CosechadorComponent,
		VendedorComponent,
		VendedorUpdateComponent,
		CosechadorUpdateComponent,
	],
	exports: [
		ProviderComponent,
		ProviderListComponent,
		ProviderCreateComponent,
		ProviderReadComponent,
		ProviderUpdateComponent,
		CosechadorComponent,
		VendedorComponent,
		VendedorUpdateComponent,
		CosechadorUpdateComponent,
	],
	providers: [
		ProviderService,
	]

})
export class ProviderModule { }
