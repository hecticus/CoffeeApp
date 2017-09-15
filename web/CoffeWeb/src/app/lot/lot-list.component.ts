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

title: string = "lista de lotes";
	@ViewChild('tableCmp') tableCmp;
	items: Lot[];
	cols: TableColumn[] = [
		new TableColumn({name:"Nombre", key: "nameLot", proportion: 1}),
		new TableColumn({name:"Finca", key: "farm.nameFarm", proportion: 2}),
		new TableColumn({name:"Status", key: "statusLot", proportion: 1})
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
			this.filter();
			this.list(this.tableService.pager.pageIndex);
  }

filter(){
		let questionFilterName =
			new QuestionFilterTextbox({
                key: 'name',
                label: 'Nombre del Lote',
                value: this.filterService.filter['name']!=undefined? this.filterService.filter['name']: '',
            });
		
		this.questionFilters = [questionFilterName];

	}

	list(page?: number){
	this.lotService.getAllSearch(this.lotService.buildRequestOptionsFinder(
			this.tableService.sort,
            "",
			this.filterService.filter,
			{pageIndex: page, pageSize: this.tableService.pager.pageSize}
		)).subscribe(params => {
			this.items = params['result'];
			this.pager = params['pager'];
			this.tableService.pager.pageIndex = page;
			this.tableCmp.deselectAll();

			for(let item of this.items)
			{
				if(item.statusLot == '1')
				{
					item.statusLot="Activo";
				}
				else
				{
					item.statusLot="No Activo";
				}
			}
		});
		
	}

	read(item: Lot){
		this.router.navigate(['./' + item.idLot], {relativeTo: this.activatedRoute});
	}

	create(){
		this.router.navigate(["./create"], {relativeTo: this.activatedRoute});
	}

	update(item: Lot){
		this.router.navigate(['./' + item.idLot + '/update'], {relativeTo: this.activatedRoute})
	}

	delete(this, item: Lot){

		this.lotService.delete(item.idLot).subscribe(any =>  {
			this.notificationService.delete(item.nameLot);
			this.tableCmp.remove(item.idLot);
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