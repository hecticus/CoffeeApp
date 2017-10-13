import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { InvoiceService } from './invoice.service';
import { Invoice } from './invoice';
import { TableService } from '../shared/table-ns/table-ns.service';
import { TableColumn } from '../shared/table-ns/table-ns-column';
import { FilterService } from '../shared/filter-ns/filter-ns.service';
import { QuestionFilterBase } from '../shared/filter-ns/question-filter/question-filter-base';
import { QuestionFilterDropdown } from '../shared/filter-ns/question-filter/question-filter-dropdown';
import { QuestionFilterTextbox } from '../shared/filter-ns/question-filter/question-filter-textbox';
import { ToolBase } from '../shared/tool-ns/tool/tool-base';
import { IconTool } from '../shared/tool-ns/tool/tool-icon';
import { Confirmation } from '../shared/confirmation-ns/confirmation-ns.service';
import { NotificationService } from '../common/notification/notification.service';

import { ProviderService } from '../provider/provider.service';

@Component({
  	templateUrl: '../common/crud/list/list.component.html'
})
export class InvoiceListComponent implements OnInit {

title: string = "Ordenes / Cosechas";
	@ViewChild('tableCmp') tableCmp;
	items: Invoice[];
	cols: TableColumn[] = [
		new TableColumn({name:"Nombre", key: "idProvider", proportion: 1}),
		new TableColumn({name:"Status", key: "statusProvider", proportion: 1})
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
	questionFilterDropdownsHarvest: QuestionFilterDropdown;

  constructor(	
    private router: Router,
		private activatedRoute: ActivatedRoute,
		private invoiceService: InvoiceService,
		private tableService: TableService,
		private filterService: FilterService,
		private notificationService:NotificationService,
		private providerService: ProviderService) {}

  ngOnInit() {
    		this.tableService.setSort(this.cols[0].key);
			this.filter();
			this.list(this.tableService.pager.pageIndex);
  }

filter(){
		
		
		this.questionFilterDropdownsHarvest =
			 new QuestionFilterDropdown({
            key: 'idProvider',
            label: 'Nombre del Cosechador:',
            value:  this.filterService.filter['idProvider']!=undefined? this.filterService.filter['idProvider']: -1,
            optionsKey: 'idProvider',
            optionsValue: 'fullNameProvider'           
        });
       
        this.providerService.getAllSearch(this.providerService.buildRequestOptionsFinder("fullName_Provider", "s","1")).subscribe(params => { 
                  this.questionFilterDropdownsHarvest.options = params['result']; 
                 }); 

		this.questionFilters = [this.questionFilterDropdownsHarvest];

	}

	list(page?: number){
	/*this.invoiceService.getAllSearch(this.invoiceService.buildRequestOptionsFinder(
			this.tableService.sort,
            "","",
			this.filterService.filter,
			{pageIndex: page, pageSize: this.tableService.pager.pageSize}
		)).subscribe(params => {
			this.items = params['result'];
			this.pager = params['pager'];
			this.tableService.pager.pageIndex = page;
			this.tableCmp.deselectAll();

			console.log(this.items);
			for(let item of this.items)
			{
				item.id=item.idInvoice;
			/*	if(item.statusInvoice == '1')
				{
					item.statusInvoice="Activo";
				}
				else
				{
					item.statusInvoice="No Activo";
				} * /
      }
		});
		*/
	}

	read(item: Invoice){
		this.router.navigate(['./' + item.idInvoice], {relativeTo: this.activatedRoute});
	}

	create(){
		this.router.navigate(["./create"], {relativeTo: this.activatedRoute});
	}

	update(item: Invoice){
		this.router.navigate(['./' + item.idInvoice + '/update'], {relativeTo: this.activatedRoute})
	}

	delete(this, item: Invoice){

		this.providerService.delete(item.idInvoice).subscribe(any =>  {
			this.notificationService.delete(item.idInvoice);
			this.tableCmp.remove(item.idInvoice);
			this.list(this.tableService.refreshPageIndexAfterRemove(1, this.pager));
		}, err => this.notificationService.error(err));
	}

	deletes(this, ids: number[]){
		this.providerService.deletes({'ids': ids}).subscribe(any =>  {
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
