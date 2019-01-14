import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'app-invoice-detail-update',
	template: `
	<div class= "container">
		<form *ngIf="form" [formGroup]="form"  (ngSubmit)="create()">
		
		</form>
	</div>
	`,
	// styleUrls: ['./invoice-detail-update.component.css']
})

export class InvoiceDetailUpdateComponent implements OnInit {

	constructor() { }

	ngOnInit() {
	}

}
