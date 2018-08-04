import { FormGroup } from '@angular/forms';
import { ActivatedRoute} from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Provider } from '../../core/models/provider';
import { ProviderService } from './provider.service';

@Component({
	styleUrls: ['./provider.component.css'],
	template: `
		<h2 class="title">Editar Provider</h2>
		<app-cosechador-update *ngIf= " selected == 'Vendedor'">
		</app-cosechador-update>
		<app-vendedor-update *ngIf= " selected == 'Cosechador'">
		</app-vendedor-update>
	`
})

export class ProviderUpdateComponent implements OnInit  {
	form: FormGroup;
	provider: Provider;
	selected = '';

	constructor(
		private activatedRoute: ActivatedRoute,
		private providerService: ProviderService,
	) {}

	ngOnInit () {
		this.activatedRoute.parent
			.params
			.subscribe(param => {
				this.providerService.getById(param['providerId']).subscribe( data => {
					this.provider = data['result'];
					this.selected = this.provider.providerType.nameProviderType;
					this.form = this.providerService.getProvider(this.provider);
				});
		});
	}

}
