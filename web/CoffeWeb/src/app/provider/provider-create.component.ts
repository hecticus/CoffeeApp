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

}
