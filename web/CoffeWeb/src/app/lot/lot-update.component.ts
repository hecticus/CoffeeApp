import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { Lot } from './lot';
import { LotService } from './lot.service';
import { NotificationService } from '../common/notification/notification.service';


@Component({
  	templateUrl: '../common/crud/update/update.component.html'
})
export class LotUpdateComponent implements OnInit {
	title: string = "update lot";
	questions: any[];
	private lot: Lot;
  constructor(
    private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private lotService: LotService,
		private notificationService:NotificationService
  ) { }

  ngOnInit(){
		this.activatedRoute.parent.params.subscribe(param => {
			console.log(param);
			this.lotService.edit(param['lotId']).subscribe(lot => {
				this.lot = lot;
				this.questions = this.lotService.getQuestions(this.lot);
			});
		});
	}

	update(form: FormGroup) {
		this.lotService.update(<Lot> this.lotService.builderObject(form.value)).subscribe(lot => {
			this.notificationService.sucessUpdate(lot.NameLot);
			this.location.back();
		}, err => this.notificationService.error(err));
	}
}