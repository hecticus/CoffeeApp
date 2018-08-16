import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';
import { UtilsModule } from './utils/utils.module';
import { BaseService } from './base.service';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,

		UtilsModule,
	],
	declarations: [
	],
	exports: [
	],
	providers: [
		BaseService,
	]

})
export class ProviderModule { }
