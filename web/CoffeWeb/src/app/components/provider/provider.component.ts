import { FilterService } from './../../core/utils/filter/filter.service';
import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'app-provider',
	template: '<router-outlet></router-outlet>',
	styleUrls: ['./provider.component.css'],
	providers: [FilterService],
})
export class ProviderComponent implements OnInit {

	constructor() { }

	ngOnInit() {
	}

}
