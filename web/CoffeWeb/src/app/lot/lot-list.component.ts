import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LotService } from './lot.service';
import { Lot } from './lot';
import { TableService } from 'app/shared/table-ns/table-ns.service';
import { TableColumn } from 'app/shared/table-ns/table-ns-column';
import { FilterService } from 'app/shared/filter-ns/filter-ns.service';
import { QuestionFilterBase } from 'app/shared/filter-ns/question-filter/question-filter-base';
import { QuestionFilterDropdown } from 'app/shared/filter-ns/question-filter/question-filter-dropdown';
import { QuestionFilterTextbox } from 'app/shared/filter-ns/question-filter/question-filter-textbox';
import { ToolBase } from 'app/shared/tool-ns/tool/tool-base';
import { IconTool } from 'app/shared/tool-ns/tool/tool-icon';
import { Confirmation } from 'app/shared/confirmation-ns/confirmation-ns.service';
import { NotificationService } from '../common/notification/notification.service';

@Component({
  	templateUrl: '../common/crud/list/list.component.html'
})
export class LotListComponent implements OnInit {

itle: string = "list lots";
	@ViewChild('tableCmp') tableCmp;
	items: Lot[];
	cols: TableColumn[] = [
		new TableColumn({key: "serial", proportion: 1}),
	];
	actions = [
		{
			html: (item) => 'update',
			click: (item) => this.update(item)
		},{
			html: (item) => 'delete',
			click: (item) => this.confirmationDelete(item)
		},
	];
	tools: ToolBase<any>[] = [
		new IconTool({
	        title: "create",
	        icon: "create",
	        clicked: this.create.bind(this)
		}),
		new IconTool({
	        title: "delete",
	        icon: "delete",
	        clicked: this.confirmationDeletes.bind(this)
		})
	];
	confirmation: Confirmation = {hiddenClose: true};
	pager: any;
	questionFilters: QuestionFilterBase<any>[] = [];

  constructor(	
    private router: Router,
		private activatedRoute: ActivatedRoute,
		private lotService: LotService,
		
		private tableService: TableService,
		private filterService: FilterService,
		private notificationService:NotificationService) { }

  ngOnInit() {
    		this.tableService.setSort(this.cols[0].key);

    /*	let storeId = this.lotService.getParamFromRoot(this.activatedRoute, 'storeId');
		if(storeId != null)
			this.filterService.filter['storeId'] = storeId;*/
		this.filter();

		this.list(this.tableService.pager.pageIndex);
  }

filter(){
		let questionFilterSerial;/* =
			new QuestionFilterTextbox({
                key: 'serial',
                label: 'serial',
                value: this.filterService.filter['serial']!=undefined? this.filterService.filter['serial']: '',
            });
		this.questionFilterMachineType  =
			new QuestionFilterDropdown({
                key: 'machineTypeId',
                label: 'machineType',
                value: this.filterService.filter['machineTypeId']!=undefined? this.filterService.filter['machineTypeId']: -1,
                optionsKey: 'name',
                changed: this.changeMachineType.bind(this)
            });
		this.questionFilterMachineBrand =
            new QuestionFilterDropdown({
                key: 'machineBrandId',
                label: 'machineBrand',
                value: this.filterService.filter['machineBrandId']!=undefined? this.filterService.filter['machineBrandId']: -1,
                optionsKey: 'name',
                changed: this.changeMachineBrand.bind(this)
            });
        this.questionFilterMachineModel =
            new QuestionFilterDropdown({
                key: 'machineModelId',
                label: 'machineModel',
                value: this.filterService.filter['machineModelId']!=undefined? this.filterService.filter['machineModelId']: -1,
                optionsKey: 'name',
                changed: this.changeMachineModel.bind(this)
            });
		let questionFilterStatusMachine =
			new QuestionFilterDropdown({
                key: 'statusMachineId',
                label: 'statusMachine.name',
                value: this.filterService.filter['statusMachineId']!=undefined? this.filterService.filter['statusMachineId']: -1,
                optionsKey: 'name',
            });
        let questionFilterStore =
			new QuestionFilterDropdown({
                key: 'storeId',
                label: 'store.name',
                value: this.filterService.filter['storeId']!=undefined? this.filterService.filter['storeId']: -1,
                optionsKey: 'name',
            });
		this.questionFilters = [
			questionFilterSerial,
			questionFilterStatusMachine,
			this.questionFilterMachineType,
			this.questionFilterMachineBrand,
			this.questionFilterMachineModel,
			questionFilterStore
		];

		this.statusMachineService.getAll(this.statusMachineService.buildRequestOptionsFinder("name", "s")).subscribe(params => {
			questionFilterStatusMachine.options = params['result'];
		});
		this.machineTypeService.getAllSearch(this.machineTypeService.buildRequestOptionsFinder("name", "s")).subscribe(params => {
			this.questionFilterMachineType.options = params['result'];
		});
		this.machineBrandService.getAllSearch(this.machineBrandService.buildRequestOptionsFinder("name", "s")).subscribe(params => {
			this.questionFilterMachineBrand.options = params['result'];
		});
		this.machineModelService.getAllSearch(this.machineModelService.buildRequestOptionsFinder("name", "s")).subscribe(params => {
			this.questionFilterMachineModel.options = params['result'];
		});
		this.storeService.getAllSearch(this.storeService.buildRequestOptionsFinder("name", "s" )).subscribe(params => {
			questionFilterStore.options = params['result'];
		});*/
	}

/*changeMachineType(machineTypeId: number){
		this.filterService.changeFilter('machineTypeId', machineTypeId);
		delete this.filterService.filter['machineModelId'];

		this.machineModelService.getAllSearch(this.machineService.buildRequestOptionsFinder(
			"name", "s", this.filterService.filter
		)).subscribe(params => {
			this.questionFilterMachineModel.options = params['result'];
			this.questionFilterMachineModel.value = -1;
		});
	}

	changeMachineBrand(machineBrandId: number){
		this.filterService.changeFilter('machineBrandId', machineBrandId);
		delete this.filterService.filter['machineModelId'];

		this.machineModelService.getAllSearch(this.machineService.buildRequestOptionsFinder(
			"name", "s", this.filterService.filter
		)).subscribe(params => {
			this.questionFilterMachineModel.options = params['result'];
			this.questionFilterMachineModel.value = -1;
		});
	}

	changeMachineModel(machineModelId: number){
		if(machineModelId != -1){
			this.machineModelService.getById(machineModelId).subscribe(machineModel => {
				this.filterService.filter['machineTypeId'] = machineModel.machineType.id;
				this.filterService.filter['machineBrandId'] = machineModel.machineBrand.id;
				this.filterService.filter['machineModelId'] = machineModel.id;

				this.questionFilterMachineType.value = machineModel.machineType.id;
				this.questionFilterMachineBrand.value = machineModel.machineBrand.id;

				this.machineModelService.getAllSearch(this.machineService.buildRequestOptionsFinder(
					"name", "s", this.filterService.filter
				)).subscribe(params => {
					this.questionFilterMachineModel.options = params['result'];
				});
			});
		}else{
			delete this.filterService.filter['machineModelId']
		}
	}*/

	list(page?: number){
	/*	this.machineService.getAllSearch(this.machineService.buildRequestOptionsFinder(
			this.tableService.sort,
            "m",
			this.filterService.filter,
			{pageIndex: page, pageSize: this.tableService.pager.pageSize}
		)).subscribe(params => {
			this.items = params['result'];
			this.pager = params['pager'];
			this.tableService.pager.pageIndex = page;
			this.tableCmp.deselectAll();
		});*/
	}

	read(item: Lot){
		this.router.navigate(['./' + item.id], {relativeTo: this.activatedRoute});
	}

	create(){
		this.router.navigate(["./create"], {relativeTo: this.activatedRoute});
	}

	update(item: Lot){
		this.router.navigate(['./' + item.id + '/update'], {relativeTo: this.activatedRoute})
	}

	delete(this, item: Lot){
		this.lotService.delete(item.id).subscribe(any =>  {
			this.notificationService.delete(item.serial);
			this.tableCmp.remove(item.id);
			this.list(this.tableService.refreshPageIndexAfterRemove(1, this.pager));
		}, err => this.notificationService.error(err));
	}

	deletes(this, ids: number[]){
		this.lotService.deletes({'ids': ids}).subscribe(any =>  {
			this.notificationService.deletes();
			this.tableCmp.removes(ids);
			this.list(this.tableService.refreshPageIndexAfterRemove(ids.length, this.pager));
		}, err => this.notificationService.error(err));
	}

	confirmationDelete(item){
		this.confirmation = {
			message: "¿Está seguro que desea eliminar el item seleccionado?",
			yesClicked: this.delete.bind(this, item),
			hiddenClose: false
		};
	}

	confirmationDeletes(){
		let ids: number[] = this.tableService.getSelectKeys();
		if(ids.length > 0)
			this.confirmation = {
				message: "¿Está seguro que desea eliminar los items seleccionados?",
				yesClicked: this.deletes.bind(this, ids),
				hiddenClose: false
			};
	}
}
