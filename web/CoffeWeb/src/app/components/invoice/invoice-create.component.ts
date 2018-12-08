import { Component, OnInit } from '@angular/core';

@Component({
	template: `
	<h2 class="title">Crear Factura</h2>
			<fieldset>
				<legend><span>Datos de la Factura</span></legend>
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
			<!--<app-cosechador-create *ngIf= "selected == 'Cosechador'"></app-cosechador-create>
			<app-vendedor-create *ngIf= "selected == 'Vendedor'"></app-vendedor-create>-->
			`,
	styleUrls: ['./invoice.component.css']
})
export class InvoiceCreateComponent implements OnInit {

	constructor() { }

	ngOnInit() {
	}

}
