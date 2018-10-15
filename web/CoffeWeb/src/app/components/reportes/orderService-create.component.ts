// import { Component, OnInit } from '@angular/core';
// import { Router, ActivatedRoute } from '@angular/router';
// import { FormGroup } from '@angular/forms';
// import { OrderService } from './orderService';
// import { OrderServiceService } from './orderService.service';
// import { HttpParams } from '@angular/common/http';

// @Component({
// 	template: `
// 		<h3 class="title" i18n="@@create-orderService">Create Service Type</h3>

// 		<form *ngIf="form" [formGroup]="form" (ngSubmit)="create()">
// 			<fieldset class="fieldset">
// 				<legend class="legend" i18n="@@data-orderService">Service Type data</legend>

// 				<div class="field-row">
// 					<div class="field-row-item">
// 						<label class="label" for="name" i18n="@@name">Name</label>
// 						<input formControlName="name" type="text" autocomplete="off">
// 						<validator [control]="form.controls['name']"></validator>
// 					</div>
// 				</div>
// 				<div class="field-row">
// 					<div class="field-row-item">
// 						<label class="label" for="name" i18n="@@workedTime">Worked time</label>
// 						<input formControlName="workedTime" type="text" autocomplete="off">
// 						<validator [control]="form.controls['workedTime']"></validator>
// 					</div>
// 					<div class="field-row-item">
// 						<label class="label" for="price" i18n="@@price">Price</label>
// 						<input formControlName="price" type="text" autocomplete="off">
// 						<validator [control]="form.controls['price']"></validator>
// 					</div>
// 				</div>
// 				<div class="field-row">
// 					<div class="field-row-item">
// 						<label class="label" for="machineModels" i18n="@@machineModels">Machine Models</label>
// 						<dropdown-multi-ns [(control)]="form.controls['machineModels']">
// 							<ng-template *ngFor="let object of machineModels" dropdownOption [value]="{id: object.id}">
// 							<div class="extend">
// 								<div class="extend-main">{{object.name || '-'}}</div>
// 								<div class="field-row">
// 									<div class="field-row-item">
// 										<span class="label" i18n="@@name">name</span>
// 										<span>{{object.comercialName || '-'}}</span>
// 									</div>
// 								</div>
// 								<div class="field-row">
// 									<div class="field-row-item">
// 										<span class="label" i18n="@@machineType">Machine Type</span>
// 										<span>{{object.machineType?.name || '-'}}</span>
// 									</div>
// 								</div>
// 								<div class="field-row">
// 									<div class="field-row-item">
// 										<span class="label" i18n="@@machineBrand">Machine Brand</span>
// 										<span>{{object.machineBrand?.name || '-'}}</span>
// 									</div>
// 								</div>
// 							</div>
// 							</ng-template>
// 						</dropdown-multi-ns>
// 						<validator [control]="form.controls['machineModels']"></validator>
// 					</div>
// 				</div>
// 				<div class="field-row">
// 					<div class="field-row-item">
// 						<label class="label" for="description" i18n="@@description">Description</label>
// 						<textarea formControlName="description"></textarea>
// 					</div>
// 				</div>
// 			</fieldset>

// 			<div class="options">
// 				<button class="btn-text" type="submit" [disabled]="!form.valid">
// 					<span i18n="@@option-save">Save</span>
// 				</button>
// 			</div>
// 		</form>
// 	`,
// })
// export class OrderServiceCreateComponent implements OnInit {
// 	form: FormGroup;
// 	machineModels: MachineModel[];

// 	constructor(
// 		private router: Router,
// 		private activatedRoute: ActivatedRoute,
// 		private orderServiceService: OrderServiceService,
// 		private machineModelService: MachineModelService,
// 		private notificationService: NotificationService,
// 	) {
// 		this.form = this.orderServiceService.toFormGroup(new OrderService);
// 	}

// 	ngOnInit() {
// 		this.machineModelService.getAll(
// 			new HttpParams().set('sort', 'comercialName').set('collection', 'id, name, comercialName, machineType(id, name), machineModel(id, name)')
// 		).subscribe(params => {
// 			this.machineModels = params['result'];
// 		});
// 	}

// 	create() {
// 		this.orderServiceService.create(<OrderService> this.form.value).subscribe(orderService => {
// 			this.notificationService.sucessInsert(orderService.name);
// 			this.router.navigate([`../${orderService.id}`], {relativeTo: this.activatedRoute});
// 		}, err => this.notificationService.error(err));
// 	}
// }
