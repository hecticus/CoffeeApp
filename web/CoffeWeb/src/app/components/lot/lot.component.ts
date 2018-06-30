import { Lot } from './../../core/models/lot';
import { LotService } from './lot.service';
import { Component, OnInit } from '@angular/core';
import { BaseService } from '../../core/base.service';

@Component({
	selector: 'app-lot',
	template: '<router-outlet></router-outlet>'
})
export class LotComponent implements OnInit {

	ngOnInit(): void {}

	constructor(
		private lotService: LotService,
	) { }


}
