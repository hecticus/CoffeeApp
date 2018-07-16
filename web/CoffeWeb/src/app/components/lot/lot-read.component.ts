import { StatusLotService } from '../status/status-lot.service';
import { Status } from '../../core/models/status';
import { Location } from '@angular/common';
import { Lot } from '../../core/models/lot';
import { LotService } from './lot.service';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
	template: `
		<h3 class="title">Lot Detail</h3>
		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" title="Update" type="button" (click)="update()">
					<i class="material-icons">edit</i>
				</button>
				<button class="btn-icon" title="Delete" type="button" (click)="openModal(template)">
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
		</div>

		<ng-template #template>
			<div class="modal-body text-center">
				<div class="dialog-title">Confirmation</div>
				<div class="dialog-message">Are you sure you want to delete this record?</div>
				<div class="dialog-options">
					<button class="btn-text green" type="button" (click)="delete()">
						<div class="text">Yes</div>
					</button>
					<button class="btn-text red" type="button" (click)="decline()" >
						<div class="text">No</div>
					</button>
				</div>
			</div>
		</ng-template>
	`
})
export class LotReadComponent implements OnInit {
	modalRef: BsModalRef;
	confirmDelete = true;
	status: Status;
	lot = new Lot();

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		private location: Location,
		private statusLotService: StatusLotService,
		private modalService: BsModalService,
		private toastr: ToastrManager

	) { }

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
				this.lotService.getById(params['lotId']).subscribe(
					data => { this.lot = data['result'];
					});
			});

		this.statusLotService.getAll().subscribe( data => {
			this.status = data['result'];
		});
	}

	openModal(template: TemplateRef<any>) {
		this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
	}

	decline(): void {
		this.modalRef.hide();
	}

	showSuccess() {
		this.toastr.successToastr('This is success toast.', 'Success!');
	}

	showInfo() {
		this.toastr.infoToastr('This is info toast.', 'Info');
	}

	update() {
		this.router.navigate(['./update'], {relativeTo: this.activatedRoute});
		console.log('estoy en update');
		console.log(this.activatedRoute);
	}

	delete() {
		this.lotService.delete(this.lot.id).subscribe( any => {
			this.showSuccess();
			let url = this.location.path();
			this.router.navigate([url.substr(0, url.lastIndexOf('/'))]);
			},  err => this.toastr.infoToastr('This is info toast.', err));
		this.modalRef.hide();
	}
}
