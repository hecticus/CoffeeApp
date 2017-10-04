import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';

import { Provider } from './provider';
import { ProviderService } from './provider.service';

import { NotificationService } from '../common/notification/notification.service';

@Component({
  	templateUrl: '../common/crud/create/create.component.html'
})
export class ProviderCreateComponent {

  title: string = "Proveedores/agregar";
	questions: any[];
	private provider: Provider;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private ProviderService: ProviderService,
		private notificationService: NotificationService,
	){}
  ngOnInit() {

			this.ProviderService.new().subscribe(provider => {
			this.provider = provider;
			this.questions = this.ProviderService.getQuestions(this.provider);
		});
  }

create(form: FormGroup) {
		this.ProviderService.create(<Provider> this.ProviderService.builderObject(form.value)).subscribe(provider => {
			this.notificationService.sucessInsert(provider.fullNameProvider);
			this.location.back();
		}, err => {
			switch(err.body.error)
			{
				case 409: this.notificationService.alert("Identificador o Nombre del Proveedor, ya registrado"); break;
				case 412: this.notificationService.alert(err.body.errorDescription); break;
				default: this.notificationService.error(err);
			}
		});
	}
}
