import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';

import { Lot } from './lot';
import { LotService } from './lot.service';

import { NotificationService } from '../common/notification/notification.service';

@Component({
  	templateUrl: '../common/crud/create/create.component.html'
})
export class LotCreateComponent {

  title: string = "Lotes/agregar";
	questions: any[];
	private lot: Lot;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private lotService: LotService,
		private notificationService: NotificationService,
	){}

  ngOnInit() {

			this.lotService.new().subscribe(lot => {
			this.lot = lot;
			this.questions = this.lotService.getQuestions(this.lot);
		});
  }

create(form: FormGroup) {
		this.lotService.create(<Lot> this.lotService.builderObject(form.value)).subscribe(lot => {
			this.notificationService.sucessInsert(lot.nameLot);
			this.location.back();
		}, err => this.notificationService.error(err));
	}
}
