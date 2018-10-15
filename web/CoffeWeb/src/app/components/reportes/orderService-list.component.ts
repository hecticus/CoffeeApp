// import { Component, OnInit } from '@angular/core';
// import { Router, ActivatedRoute } from '@angular/router';
// import { BaseService } from 'app/core/base.service';
// import { OrderService} from './orderService';
// import { OrderServiceService } from './orderService.service';
// import { MachineTypeService } from '../machineType/machineType.service';
// import { MachineBrandService } from '../machineBrand/machineBrand.service';
// import { MachineModelService } from '../machineModel/machineModel.service';
// import { Table } from 'app/shared/table-ns/table-ns';
// import { FilterService } from 'app/shared/filter-ns/filter-ns.service';
// import { NotificationService} from 'app/shared/notification/notification.service';
// import { MachineType } from 'app/machineType/machineType';
// import { MachineBrand } from 'app/machineBrand/machineBrand';
// import { MachineModel } from 'app/machineModel/machineModel';
// import { HttpParams } from '@angular/common/http';

// @Component({
// 	template: `
// 		<h3 class="title" i18n="@@orderServices">Service Types</h3>

// 		<div class="filter fieldset-wrap">
// 			<div class="field-row">
// 				<div class="field-row-item">
// 					<label class="label" i18n="@@name">Name</label>
// 					<input type="text" placeholder="None" i18n-placeholder="None" (change)="filterService.put('name', $event.target.value)">
// 				</div>
// 				<div class="field-row-item">
// 					<label class="label" i18n="@@machineModel">Machine Model</label>
// 					<div class="custom-select">
// 						<select [(ngModel)]="filterService.filter['machineModelId']" (change)="filterService.put('machineModelId', $event.target.value); changeMachineModel($event.target.value)">
// 							<option value="undefined" i18n="@@select-none">None</option>
// 							<option *ngFor="let opt of machineModels" [value]="opt.id">{{opt.name}}</option>
// 						</select>
// 					</div>
// 				</div>
// 				<div class="field-row-item">
// 					<label class="label" i18n="@@machineType">Machine Type</label>
// 					<div class="custom-select">
// 						<select [(ngModel)]="filterService.filter['machineTypeId']" (change)="filterService.put('machineTypeId', $event.target.value); changeMachineType($event.target.value)">
// 							<option value="undefined" i18n="@@select-none">None</option>
// 							<option *ngFor="let opt of machineTypes" [value]="opt.id">{{opt.name}}</option>
// 						</select>
// 					</div>
// 				</div>
// 				<div class="field-row-item">
// 					<label class="label" i18n="@@machineBrand">Machine Brand</label>
// 					<div class="custom-select">
// 						<select [(ngModel)]="filterService.filter['machineBrandId']" (change)="filterService.put('machineBrandId', $event.target.value); changeMachineBrand($event.target.value)">
// 							<option value="undefined" i18n="@@select-none">None</option>
// 							<option *ngFor="let opt of machineBrands" [value]="opt.id">{{opt.name}}</option>
// 						</select>
// 					</div>
// 				</div>
// 			</div>
// 			<div class="container-button-filter">
// 				<button class="btn-icon" i18n-title="@@option-search"  title="Search" type="button" (click)="list(0)">
// 					<i class="material-icons">search</i>
// 				</button>
// 			</div>
// 		</div>

// 		<div class="tool-bar-wrap both-side">
// 			<div class="right row">
// 				<button class="btn-icon" i18n-title="@@option-create" type="button" (click)="create()" *hasPermission="['OrderServices.create']">
// 					<i class="material-icons">add</i>
// 				</button>
// 				<ng-template [hasPermission]="['OrderServices.delete']">
// 					<button class="btn-icon" i18n-title="@@option-delete"  title="Delete" type="button" (click)="confirmDelete = false" *ngIf="table.selector.length() > 0" >
// 						<i class="material-icons">delete</i>
// 					</button>
// 				</ng-template>
// 			</div>
// 		</div>

// 		<table-ns
// 			[items]="items"
// 			(read)="read($event)"
// 			(list)="list($event)">
// 			<ng-template tableColumn [propertyKey]="'name'" [grow]="2">
// 				<span class="label" i18n="@@name">Name</span>
// 			</ng-template>
// 			<ng-template tableColumn [propertyKey]="'workedTime'">
// 				<span class="label" i18n="@@workedTime">Worked time (hours)</span>
// 			</ng-template>
// 			<ng-template tableColumn [propertyKey]="'price'">
// 				<span class="label" i18n="@@price">Price</span>
// 			</ng-template>
// 		</table-ns>

// 		<pagination *ngIf="table.pager && items"
// 			[totalItems]="table.pager.totalEntities"
// 			[initPage]="table.pager.pageIndex+1"
// 			[pageSize]="table.pager.pageSize"
// 			(paginated)="list($event)">
// 		</pagination>

// 		<context-menu #basicMenu>
// 			<ng-template contextMenuItem (execute)="getMachineModels($event.item)">
// 				<div class="item">
// 					<span i18n="@@machineModels">Machine Models</span>
// 				</div>
// 			</ng-template>
// 			<ng-template contextMenuItem divider="true"></ng-template>
// 			<ng-template contextMenuItem (execute)="update($event.item)">
// 				<div class="item">
// 					<i class="material-icons">edit</i>
// 					<span i18n="@@option-update">Update</span>
// 				</div>
// 			</ng-template>
// 			<ng-template contextMenuItem divider="true"></ng-template>
// 			<ng-template contextMenuItem (execute)="confirmDelete = false">
// 				<div class="item">
// 					<i class="material-icons">delete</i>
// 					<span i18n="@@option-delete">Delete</span>
// 				</div>
// 			</ng-template>
// 		</context-menu>

// 		<modal-ns [(closed)]="confirmDelete">
// 			<ng-template modalContentDirective>
// 				<div class="dialog-content">
// 					<div class="dialog-title" i18n="@@confirm">Confirmation</div>
// 					<div class="dialog-message" i18n="@@confirm-deletes">Are you sure you want to delete the selected items?</div>
// 					<div class="dialog-options">
// 						<button class="btn-text red" type="button" (click)="confirmDelete = true">
// 							<span i18n="@@option-no">No</span>
// 						</button>
// 						<button class="btn-text green" type="button" (click)="deletes(); confirmDelete = true">
// 							<span i18n="@@option-yes">Yes</span>
// 						</button>
// 					</div>
// 				</div>
// 			</ng-template>
// 		</modal-ns>
// 	`,
// })
// export class OrderServiceListComponent implements OnInit {
// 	items: OrderService[];
// 	confirmDelete = true;
// 	machineTypes: MachineType[];
// 	machineBrands: MachineBrand[];
// 	machineModels: MachineModel[];

// 	constructor(
// 		private router: Router,
// 		private activatedRoute: ActivatedRoute,
// 		private orderServiceService: OrderServiceService,
// 		private machineTypeService: MachineTypeService,
// 		private machineBrandService: MachineBrandService,
// 		private machineModelService: MachineModelService,
// 		public table: Table,
// 		public filterService: FilterService,
// 		private notificationService: NotificationService
// 	) {}

// 	ngOnInit() {
// 		if (!this.table.sort) {
// 			this.table.sort = 'name';
// 		}
// 		this.filter();
// 		this.list(this.table.pager.pageIndex);
// 	}

// 	filter() {
// 		let httpParams = new HttpParams().set('sort', 'name').set('collection', 'id, name');
// 		this.machineTypeService.getAll(httpParams).subscribe(params => {
// 			this.machineTypes = params['result'];
// 		});
// 		this.machineBrandService.getAll(httpParams).subscribe(params => {
// 			this.machineBrands = params['result'];
// 		});
// 		this.machineModelService.getAll(httpParams).subscribe(params => {
// 			this.machineModels = params['result'];
// 		});
// 	}

// 	changeMachineType(machineTypeId: any) {
// 		delete this.filterService.filter['machineModelId'];

// 		this.machineModelService.getAll(
// 			BaseService.jsonToHttpParams({sort: 'name', collection: 'id, name', ...this.filterService.filter})
// 		).subscribe(params => {
// 			this.machineModels = params['result'];
// 		});
// 	}

// 	changeMachineBrand(machineBrandId: any) {
// 		delete this.filterService.filter['machineModelId'];

// 		this.machineModelService.getAll(
// 			BaseService.jsonToHttpParams({sort: 'name', collection: 'id, name', ...this.filterService.filter})
// 		).subscribe(params => {
// 			this.machineModels = params['result'];
// 		});
// 	}

// 	changeMachineModel(machineModelId: any) {
// 		if (machineModelId && machineModelId != 'undefined') {
// 			this.machineModelService.getById(machineModelId).subscribe(machineModel => {
// 				this.filterService.filter['machineTypeId'] = machineModel.machineType.id;
// 				this.filterService.filter['machineBrandId'] = machineModel.machineBrand.id;

// 				this.machineModelService.getAll(
// 					BaseService.jsonToHttpParams({sort: 'name', collection: 'id, name', ...this.filterService.filter})
// 				).subscribe(params => {
// 					this.machineModels = params['result'];
// 				});
// 			});
// 		} else {
// 			delete this.filterService.filter['machineModelId'];
// 		}
// 	}

// 	list(page = 0) {
// 		let httpParams = BaseService.jsonToHttpParams({
// 			sort: this.table.sort,
// 			collection: 'id, name, price, workedTime',
// 			'pager.index': page,
// 			'pager.size': this.table.pager.pageSize,
// 			...this.filterService.filter
// 		});
// 		this.orderServiceService.getAll(httpParams).subscribe(data => {
// 			this.items = data['result'];
// 			this.table.pager = data['pager'];
// 			this.table.selector.clear();
// 		});
// 	}

// 	getMachineModels(item: OrderService) {
// 		this.router.navigate(['./' + item.id + '/machineModels'], {relativeTo: this.activatedRoute});
// 	}

// 	/*getOrderRequests(item: OrderRequest) {
// 		this.router.navigate(['./' + item.id + '/orderRequests'], {relativeTo: this.activatedRoute});
// 	}*/

// 	read(item: OrderService) {
// 		this.router.navigate(['./' + item.id], {relativeTo: this.activatedRoute});
// 	}

// 	create() {
// 		this.router.navigate(['./create'], {relativeTo: this.activatedRoute});
// 	}

// 	update(item: OrderService) {
// 		this.router.navigate(['./' + item.id + '/update'], {relativeTo: this.activatedRoute});
// 	}

// 	deletes() {
// 		const length = this.table.selector.length();
// 		const items = this.table.selector.getItems();
// 		const pageIndex = (this.table.pager.pageIndex > 0 && this.table.pager.totalEntitiesPerPage === length) ?
// 			--this.table.pager.pageIndex : this.table.pager.pageIndex;

// 		if (length === 1) {
// 			this.orderServiceService.delete(items[0].id).subscribe(() => {
// 				this.notificationService.sucessDelete();
// 				this.list(pageIndex);
// 			}, err => this.notificationService.error(err));
// 		} else if (length > 1) {
// 			this.orderServiceService.deletes(items).subscribe(() => {
// 				this.notificationService.sucessDelete();
// 				this.list(pageIndex);
// 			}, err => this.notificationService.error(err));
// 		}
// 	}
// }
