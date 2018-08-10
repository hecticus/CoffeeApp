import { Component, OnInit } from '@angular/core';
import { FilterService } from './../../core/utils/filter/filter.service';

@Component({
	selector: 'app-invoice',
	template: '<router-outlet></router-outlet>',
	styleUrls: ['./invoice.component.css'],
	providers: [FilterService],
})
export class InvoiceComponent implements OnInit {

	constructor() { }

	ngOnInit() {
	}

}
