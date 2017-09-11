import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ProviderService } from './provider.service';
import { Provider } from './provider'
import { ToolBase } from 'app/shared/tool-ns/tool/tool-base';
import { IconTool } from 'app/shared/tool-ns/tool/tool-icon';
import { Confirmation } from 'app/shared/confirmation-ns/confirmation-ns.service';
import { NotificationService } from '../common/notification/notification.service';

@Component({
  	templateUrl: '../common/crud/read/read.component.html'
})
export class ProviderReadComponent implements OnInit {

	title: string = "Proveedor/mostrar";
	tools: ToolBase<any>[] = [
		new IconTool({
	        title: "update",
	        icon: "update",
	        clicked: this.update.bind(this)
		}),
		new IconTool({
	        title: "delete",
	        icon: "delete",
	        clicked: this.confirmationDelete.bind(this)
		})
	];
	answers: any[];
	confirmation: Confirmation = {hiddenClose: true};
	private provider: Provider;

  constructor(
    private router: Router, 
		private activatedRoute: ActivatedRoute, 
		private location: Location, 
		private providerService: ProviderService,
		private notificationService: NotificationService
  ) { }

  ngOnInit(){
		this.activatedRoute
			.params
			.subscribe(param => {
        console.log(param);
				this.providerService.getById(param['providerId'])
					.subscribe(provider => { 
						this.provider = provider;
										console.log(provider);
						this.answers = this.providerService.getAnswers(provider);
					});
			});
	}

	update(){
		this.router.navigate(['./update/'], {relativeTo: this.activatedRoute});
	}

	delete(this){
		this.providerService.delete(this.provider.idLot)
			.subscribe(any => {
				this.notificationService.delete();
				this.location.back();
			}, err => this.notificationService.error(err));

	}

	confirmationDelete(){
		this.confirmation = {
			message: "¿Está seguro que desea eliminar el item?",
			yesClicked: this.delete.bind(this),
			hiddenClose: false
		};
	}
}
