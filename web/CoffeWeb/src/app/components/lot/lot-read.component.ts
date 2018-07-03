import { Location } from '@angular/common';
import { Lot } from 'src/app/core/models/lot';
import { LotService } from './lot.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
	template: `
		<h3 class="title">Lot Detail</h3>
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
				<div class="legend">Lot Data</div>

				<div class="wrap-fields">
					<div>
						<span class="label">Name</span>
						<span class="output">{{lot.nameLot|| '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Farm</span>
						<span class="output">{{lot.farm?.nameFarm || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Area</span>
						<span class="output">{{lot.areaLot || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Height</span>
						<span class="output">{{lot.heighLot || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Price Lot</span>
						<span class="output">{{lot.priceLot || '-'}}</span>
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
export class LotReadComponent implements OnInit {
	confirmDelete = true;
	lot = new Lot();

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private location: Location,

	) { }

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
				this.lotService.getById(params['lotId']).subscribe(
					data => { this.lot = data['result'];
				console.log(this.lot); }
				);
			});
	}

	// update() {
	// 	this.router.navigate(['./update'], {relativeTo: this.activatedRoute});
	// }

	delete(this) {
		this.lotService.delete(this.lot.id).subscribe( any => {
			let url = this.location.path();
			this.router.navigate([url.substr(0, url.lastIndexOf('/'))]);
			} // }, err => this.notificationService.error(err));
		);
	}
}
