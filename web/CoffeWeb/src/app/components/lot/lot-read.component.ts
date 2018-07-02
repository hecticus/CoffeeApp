import { Lot } from './../../core/models/lot';
import { LotService } from './lot.service';
import { Component, OnInit } from '@angular/core';
import { BaseService } from '../../core/base.service';

@Component({
	template: '<h1>jjj</h1>'
})
export class LotReadComponent implements OnInit {

	ngOnInit(): void {}

	constructor(
		private lotService: LotService,
	) { }


}
