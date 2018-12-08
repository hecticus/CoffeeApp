import { Component, OnInit } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';

@Component({
	selector: 'app-purchase-create',
	template: `
	<h2 class="title">Coffe</h2>
			`,
	styleUrls: ['./invoice.component.css']
})

export class PurchaseCreateComponent implements OnInit {

	operacion: Operacion [] = [
		{ id: 1, name: 'Nueva Cosecha' },
		{ id: 2, name: 'Nueva Compra' }
	];

	constructor() { }

	ngOnInit() {
	}

}
