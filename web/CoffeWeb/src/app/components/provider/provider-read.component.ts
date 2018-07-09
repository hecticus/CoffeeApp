import { ProviderType } from '../../core/models/provider-type';
import { ProviderTypeService } from '../provider-type/provider-type.service';
import { ProviderService } from './provider.service';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { LotService } from '../lot/lot.service';
import { Provider } from '../../core/models/provider';

@Component({
	template: `
		<h3 class="title">Provider Detail</h3>
		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" title="Update" type="button" (click)="update()">
					<i class="material-icons">edit</i>
				</button>
				<button class="btn-icon" title="Delete" type="button" (click)="confirmDelete = false">
					<i class="material-icons">delete</i>
				</button>
			</div>
		</div>

		<div class="answer">
			<div class="fieldset">
				<div class="legend">Provider Data</div>

				<div class="wrap-fields">
					<div>
						<span class="label">Provider Type</span>
						<span class="output">{{provider.providerType?.id || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Name</span>
						<span class="output">{{provider.nameProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Code Provider</span>
						<span class="output">{{provider.nitProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Address</span>
						<span class="output">{{provider.addressProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Phone Number</span>
						<span class="output">{{provider.numberProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Email Provider</span>
						<span class="output">{{provider.emailProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Contact Name</span>
						<span class="output">{{provider.contactNameProvider || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Status</span>
						<span class="output">{{provider.statusProvider || '-'}}</span>
					</div>
				</div>
			</div>

			<app-modal [(closed)]="confirmDelete">
				<ng-template modalContentDirective>
					<div class="dialog-content">
						<div class="dialog-title" >Confirmation</div>
						<div class="dialog-message">Are you sure you want to delete this record?</div>
						<div class="dialog-options">
							<button class="btn-text red" type="button" (click)="confirmDelete = true">
								<div class="text">No</div>
							</button>
							<button class="btn-text green" type="button" (click)="delete(); confirmDelete = true">
								<div class="text">Yes</div>
							</button>
						</div>
					</div>
				</ng-template>
			</app-modal>
	`
})
export class ProviderReadComponent implements OnInit {
	confirmDelete = true;
	provider = new Provider();
	// providerType: ProviderType;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private providerService: ProviderService,
	) { }

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
				this.providerService.getById(params['providerId']).subscribe(
					data => { this.provider = data['result'];
				console.log(this.provider); }
				);
			});
		// this.providerTypeService.getById(this.provider.providerType.id).subscribe(
		// 	data => { this.providerType = data['result'];
		// 	console.log(this.providerType);
		// 	});
	}

	update() {
		console.log(this.activatedRoute);
		this.router.navigate(['./update'], {relativeTo: this.activatedRoute});
	}

	delete(this) {
		this.lotService.delete(this.lot.id).subscribe( any => {
			let url = this.location.path();
			this.router.navigate([url.substr(0, url.lastIndexOf('/'))]);
			} // }, err => this.notificationService.error(err));
		);
	}
}
