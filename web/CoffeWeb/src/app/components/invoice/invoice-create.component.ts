import { Component, OnInit } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';

@Component({
	template: `
	<h2 class="title">Crear Factura</h2>
			<fieldset>
				<div class="wrap-fields">
					<div class="field">
						<mat-form-field  required class="example-full-width">
							<mat-select [(value)]="selected">
							  	<mat-option  *ngFor="let o of operacion" [value]="o.name" >
									{{o.name}}
								</mat-option>
							</mat-select>
						<mat-label><b>Tipo de Factura</b></mat-label>
					  </mat-form-field>
					</div>
				</div>
			</fieldset>
			<!---->
			<app-harvest-create *ngIf= "selected == 'Nueva Cosecha'"></app-harvest-create>
			<app-purchase-create *ngIf= "selected == 'Nueva Compra'"></app-purchase-create>
			<app-purchase-create></app-purchase-create>
			`,
	styleUrls: ['./invoice.component.css']
})
export class InvoiceCreateComponent implements OnInit {
	selected = '';

	operacion: Operacion [] = [
		{ id: 1, name: 'Nueva Cosecha' },
		{ id: 2, name: 'Nueva Compra' }
	];

	constructor() { }

	ngOnInit() {
	}

}
