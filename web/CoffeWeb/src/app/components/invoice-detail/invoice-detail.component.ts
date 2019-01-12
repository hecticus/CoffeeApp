import { Component, OnInit } from '@angular/core';
import { FilterService } from 'src/app/core/utils/filter/filter.service';

@Component({
	selector: 'app-invoice-detail',
	// template: '<router-outlet></router-outlet>',
	template: '<h2>Hola</h2>',
	styleUrls: ['./invoice-detail.component.css'],
	providers: [FilterService],
})
export class InvoiceDetailComponent implements OnInit {

	constructor() { }

	ngOnInit() {
	}

}
