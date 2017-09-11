import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { Provider } from './provider';
import { ProviderService } from './provider.service';
import { NotificationService } from '../common/notification/notification.service';

@Component({
  	templateUrl: '../common/crud/update/update.component.html'
})
export class ProviderUpdateComponent implements OnInit {
title: string = "Proveedor/actualizar";
	questions: any[];
	private provider: Provider;
  constructor(
    private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private providerService: ProviderService,
		private notificationService:NotificationService
  ) { }

  ngOnInit(){
		this.activatedRoute.parent.params.subscribe(param => {
      console.log(param);
			this.providerService.edit(param['providerId']).subscribe(Provider => {
				this.provider = Provider;
				this.questions = this.providerService.getQuestions(this.provider);
			});
		});
	}

	update(form: FormGroup) {
		this.providerService.update(<Provider> this.providerService.builderObject(form.value)).subscribe(provider => {
			this.notificationService.sucessUpdate(provider.fullNameProvider);
			this.location.back();
		}, err => this.notificationService.error(err));
	}
}