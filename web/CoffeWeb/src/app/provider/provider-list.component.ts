import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ProviderService } from './provider.service';
import { Provider } from './provider';
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

import { ProviderType } from '../providerType/providerType';
import { ProviderTypeService } from '../providerType/providerType.service';
import { DropdownQuestion } from '../shared/dynamic-form/question/question-dropdown';

@Component({
  	templateUrl: '../common/crud/list/list.component.html'
})
export class ProviderListComponent implements OnInit {

title: string = "Lista Proveerdores";
	@ViewChild('tableCmp') tableCmp;
	items: Provider[];
	cols: TableColumn[] = [
		new TableColumn({name:"Nombre", key: "fullNameProvider", proportion: 1}),
		new TableColumn({name:"Telefono", key: "phoneNumberProvider", proportion: 1}),
		new TableColumn({name:"Status", key: "statusProvider", proportion: 1}),
		new TableColumn({name:"Tipo", key: "providerType.nameProviderType", proportion: 1})
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
	questionFilterDropdownsProviderType: QuestionFilterDropdown;

  constructor(	
    private router: Router,
		private activatedRoute: ActivatedRoute,
		private providerService: ProviderService,
		private tableService: TableService,
		private filterService: FilterService,
		private notificationService:NotificationService,
		private providerTypeService:ProviderTypeService) { }

  ngOnInit() {
    		this.tableService.setSort(this.cols[0].key);
			this.filter();
			this.list(this.tableService.pager.pageIndex);
  }

filter(){
		let questionFilterName =
			new QuestionFilterTextbox({
                key: 'name',
                label: 'Nombre del Proveedor',
                value: this.filterService.filter['fullNameProvider']!=undefined? this.filterService.filter['fullNameProvider']: '',
            });


		this.questionFilterDropdownsProviderType  =
			new QuestionFilterDropdown({
                key: 'idProviderType',
                label: 'Tipo del proveedor:',
                value: this.filterService.filter['idProviderType']!=undefined? this.filterService.filter['idProviderType']: -1,
                optionsKey: 'idProviderType',
                optionsValue: 'nameProviderType',
            //    changed: this.changeProviderType.bind(this)
            });
 	
		this.questionFilters = [questionFilterName,
			this.questionFilterDropdownsProviderType
			];

			this.providerTypeService.getAll(this.providerTypeService.buildRequestOptionsFinder("name_provider_type", "s")).subscribe(params => { 
               this.questionFilterDropdownsProviderType.options = params['result']; 
		});



	}

	/*	changeProviderType(idProviderType: number){
		this.filterService.changeFilter('idProviderType', idProviderType);
		delete this.filterService.filter['nameProviderType'];

		this.providerTypeService.getAll(this.providerTypeService.buildRequestOptionsFinder(
			"name_provider_type", "s", "", this.filterService.filter
		)).subscribe(params => {
			this.questionFilterDropdownsProviderType.options = params['result'];
			this.questionFilterDropdownsProviderType.value = -1;
		});
		}*/

	list(page?: number){

		console.log(this.filterService.filter);
	this.providerService.getAllSearch(this.providerService.buildRequestOptionsFinder(
			this.tableService.sort,
            "", "1",
			this.filterService.filter,
			{pageIndex: page, pageSize: this.tableService.pager.pageSize}
		)).subscribe(params => {
			this.items = params['result'];
			this.pager = params['pager'];
			this.tableService.pager.pageIndex = page;
			this.tableCmp.deselectAll();

			for(let item of this.items)
			{
				item.id=item.idProvider;
				if(item.statusProvider == '1')
				{
					item.statusProvider="Activo";
				}
				else
				{
					item.statusProvider="No Activo";
				}
      }
		});
		
	}

	read(item: Provider){
		this.router.navigate(['./' + item.idProvider], {relativeTo: this.activatedRoute});
	}

	create(){
		this.router.navigate(["./create"], {relativeTo: this.activatedRoute});
	}

	update(item: Provider){
		this.router.navigate(['./' + item.idProvider + '/update'], {relativeTo: this.activatedRoute})
	}

	delete(this, item: Provider){

		this.providerService.delete(item.idProvider).subscribe(any =>  {
			this.notificationService.delete(item.fullNameProvider);
			this.tableCmp.remove(item.idProvider);
			this.list(this.tableService.refreshPageIndexAfterRemove(1, this.pager));
		}, err => {
		  let error = err.json();
			if(error["error"]==409)	this.notificationService.alert(error["message"])
			else this.notificationService.error(error["message"])
			console.log(error)
		
		});
	}

	deletes(this, ids: number[]){
		this.providerService.deletes({'ids': ids}).subscribe(any =>  {
			this.notificationService.deletes();
			this.tableCmp.removes(ids);
			this.list(this.tableService.refreshPageIndexAfterRemove(ids.length, this.pager));
		}, err => {
		  let error = err.json();
			if(error["error"]==409)	this.notificationService.alert(error["message"])
			else this.notificationService.error(error["message"])
			console.log(error)
		
		});
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
