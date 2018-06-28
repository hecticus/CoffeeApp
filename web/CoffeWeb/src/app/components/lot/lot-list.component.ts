import { Hero } from './../heroes/hero';
import { TablaService } from './../../core/table/tabla.service';
import { LotService } from './lot.service';
import { Component, OnInit } from '@angular/core';
import { BaseService } from '../../core/base.service';
import { Lot } from '../../core/models/lot';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
	template: `
		<h1>Hero</h1>
	`
})
export class LotListComponent implements OnInit {
	confirmDelete = true;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		public tableService: TablaService,
		// public filterService: FilterService,
		// private notificationService: NotificationService
	) {}

	ngOnInit() {
	}


}
