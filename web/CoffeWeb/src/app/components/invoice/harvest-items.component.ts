// import { Component, Input } from '@angular/core';
// import { FormGroup } from '@angular/forms';

// @Component({
// 	// moduleId: module.id,
// 	selector: 'app-harvest-items',
// 	styleUrls: ['./invoice.component.css'],
// 	// templateUrl: `
// 	// <div [formGroup]="itemTypesForm">

// 	// 	<div class="wrap-fields">
// 	// 		<div class="field form-field">
// 	// 			<mat-form-field class="example-full-width">
// 	// 				<mat-select required>
// 	// 					<mat-option>Ninguna</mat-option>
// 	// 					<mat-option *ngFor="let f of farms" [value]="f.id"> {{f.nameFarm}} </mat-option>
// 	// 				</mat-select>
// 	// 				<mat-label><b>Granja</b></mat-label>
// 	// 			</mat-form-field>
// 	// 		</div>
// 	// 	</div>

// 	// 	<div class="wrap-fields">
// 	// 		<div class="field form-field">
// 	// 			<mat-form-field class="example-full-width">
// 	// 				<mat-select required [formControl]="itemTypesForm.controls['lot']">
// 	// 					<mat-option *ngFor="let l of lots" [value]="{id: l.id}">{{l.id}}</mat-option>
// 	// 				</mat-select>
// 	// 				<mat-label><b>Lote</b></mat-label>
// 	// 			</mat-form-field>
// 	// 			<app-validator [control]="itemTypesForm.controls['lot']"></app-validator>
// 	// 		</div>
// 	// 	</div>

// 	// 	<div class="wrap-fields">
// 	// 		<div class="field form-field">
// 	// 			<mat-form-field class="example-full-width">
// 	// 				<input matInput required formControlName="amountInvoiceDetail" placeholder="Cantidad" class="example-right-align">
// 	// 			</mat-form-field>
// 	// 			<app-validator [control]="itemTypesForm.controls['amountInvoiceDetail']"></app-validator>
// 	// 		</div>
// 	// 	</div>

// 	// 	<div class="wrap-fields">
// 	// 		<div class="field">
// 	// 			<mat-form-field required class="example-full-width">
// 	// 				<input matInput formControlName="noteInvoiceDetail" placeholder="Name">
// 	// 			</mat-form-field>
// 	// 			<app-validator  [control]="itemTypesForm.controls['noteInvoiceDetail']"></app-validator>
// 	// 		</div>
// 	// 	</div>

// 	// </div>
// 	// `
// })
// export class HarvestItemsComponent {

// 	@Input() group: string;

// 	public itemTypesForm: FormGroup;

// }
