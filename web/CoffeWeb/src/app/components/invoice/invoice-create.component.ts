import { Component, OnInit } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';

@Component({
	styleUrls: ['./invoice.component.css'],
	template: `
	<h2 class="title"><span>Crear Factura</span></h2>
			<fieldset>
			<!--
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
			-->

			<mat-radio-group [(ngModel)]="action">
				<mat-radio-button class="example-radio-button" class="example-radio-button" *ngFor="let season of operacion" [value]="season.name">
					{{season.name}}
				</mat-radio-button>
			</mat-radio-group>

			<app-harvest-create *ngIf= "action == 'Cosecha'"></app-harvest-create>
			<app-purchase-create *ngIf= "action == 'Compra'"></app-purchase-create>
	`
})
export class InvoiceCreateComponent implements OnInit {
	selected = '';
	action : string;

	operacion: Operacion [] = [
		{ id: 1, name: 'Cosecha' },
		{ id: 2, name: 'Compra' }
	];

	constructor() { }

	ngOnInit() {
	}

}
