import { Component, OnInit } from '@angular/core';
import { Operacion } from 'src/app/core/models/Operacion';

@Component({
	selector: 'app-harvest-create',
	template: `
	<h2 class="title">Harvest</h2>
			`,
	styleUrls: ['./invoice.component.css']
})

export class HarvestCreateComponent implements OnInit {

	operacion: Operacion [] = [
		{ id: 1, name: 'Nueva Cosecha' },
		{ id: 2, name: 'Nueva Compra' }
	];

	constructor() { }

	ngOnInit() {
	}

}
