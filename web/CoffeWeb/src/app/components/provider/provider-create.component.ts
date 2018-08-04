import { BaseService } from '../../core/base.service';
import { Component, OnInit } from '@angular/core';
import { ProviderType } from '../../core/models/provider-type';
import { ProviderTypeService } from '../provider-type/provider-type.service';

@Component({
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Crear Proveedor</h2>
			<fieldset>
				<legend><span>Datos del Proveedor</span></legend>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<mat-select [(value)]="selected">
							  	<mat-option  *ngFor="let f of provType" [value]="f.nameProviderType" >
									{{f.nameProviderType}}
								</mat-option>
							</mat-select>
						<mat-label><b>Tipo de Proveedor</b></mat-label>
					  </mat-form-field>
					</div>
				</div>
			</fieldset>
			<app-cosechador *ngIf= "selected == 'Cosechador'"></app-cosechador>
			<app-vendedor *ngIf= "selected == 'Vendedor'"></app-vendedor>
  `
})

export class ProviderCreateComponent implements OnInit  {
	provType: ProviderType[];
	selected = '';

	constructor(
		private providerTypeService: ProviderTypeService
	) {}

	ngOnInit () {
		let paramStatus = BaseService.jsonToHttpParams(
			{collection: 'id,nameProviderType'}
		);

		this.providerTypeService.getAll(paramStatus).subscribe(
			data => {
				this.provType = data['result'];
			}
		);
	}

}
