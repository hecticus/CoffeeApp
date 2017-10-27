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

//////////////
import {IMyDpOptions} from 'mydatepicker';
import {IMyDateModel, IMyInputFieldChanged, IMyCalendarViewChanged, IMyInputFocusBlur, IMyMarkedDate, IMyDate, IMySelector} from 'mydatepicker';
import {MyDatePicker}  from 'mydatepicker';
//////////////

import { ProviderService } from '../provider/provider.service';
import { Provider } from '../provider/provider';
import { ProviderType } from '../providerType/providerType';
import { ProviderTypeService } from '../providerType/providerType.service';

@Component({
  	templateUrl: 'crudInvoice/list/list.component.html'
})
export class InvoiceListComponent implements OnInit {
///////////////////////////////////////////////////////////
/** https://github.com/kekeh/mydatepicker **/
 @ViewChild('startDatePicker') startDatePicker: MyDatePicker;
 @ViewChild('endDatePicker') endDatePicker: MyDatePicker;

    private myDatePickerNormalOptions: IMyDpOptions = {
        todayBtnTxt: 'Hoy',
		firstDayOfWeek: 'do',
        dateFormat: 'yyyy-mm-dd',
        height: '34px',
        width: '210px',
        monthSelector: true,
        yearSelector: true,
        minYear: 1970,
        maxYear: 2900,
        showClearDateBtn: true,
        showDecreaseDateBtn: true,
        showIncreaseDateBtn: true,
        monthLabels: {
            1: 'Enero',
            2: 'Febrero',
            3: 'Marzo',
            4: 'Abril',
            5: 'Mayo',
            6: 'Junio',
            7: 'Julio',
            8: 'Agosto',
            9: 'Septiembre',
            10: 'Octubre',
            11: 'Noviembre',
            12: 'Diciembre'
        }
    };
    private startDateValue:string = '';
	 private endDateValue:string = '';

    private rangeDate: string = 'Seleccione rango de fecha';
	private startDate: string = 'Fecha de Inicio';
	private endDate: string = 'Fecha de Fin';
    private disabled: boolean = false;
	private aux: boolean = false;
	private idProviderType: number= -2;

///////////////////////////////////////////////////////////

title: string = "Ordenes / Cosechas";
	@ViewChild('tableCmp') tableCmp;
	items: Invoice[];
	cols: TableColumn[] = [
		new TableColumn({name:"Proveedor", key: "provider.fullNameProvider", proportion: 1}),
		new TableColumn({name:"Fecha de la orden", key: "startDateInvoice", proportion: 1}),
		new TableColumn({name:"Estatus de la Orden", key: "statusInvoice", proportion: 1})
	];
	confirmation: Confirmation = {hiddenClose: true};
	pager: any;
	questionFilters: QuestionFilterBase<any>[] = [];
	questionFilterDropdownsProvider: QuestionFilterDropdown;
	questionFilterDropdownsProviderType: QuestionFilterDropdown;


  constructor(	
	    private router: Router,
		private activatedRoute: ActivatedRoute,
		private invoiceService: InvoiceService,
		private tableService: TableService,
		private filterService: FilterService,
		private notificationService:NotificationService,
		private providerService: ProviderService,
		private providerTypeService:ProviderTypeService) {}

  ngOnInit() {
   	this.tableService.setSort("-"+this.cols[1].key);
	this.filter();
	this.list(this.tableService.pager.pageIndex);
   }

  ///////////////////////////////////////////////////
    onDateChanged(event: IMyDateModel) {
		this.startDateValue = event.formatted;
    }

	onEndDateChanged(event: IMyDateModel) {
		this.endDateValue = event.formatted;
    }

    onInputFieldChanged(event: IMyInputFieldChanged) {
        //console.log('onInputFieldChanged(): Value: ', event.value, ' - dateFormat: ', event.dateFormat, ' - valid: ', event.valid);
    }

    onCalendarViewChanged(event: IMyCalendarViewChanged) {
        //console.log('onCalendarViewChanged(): Year: ', event.year, ' - month: ', event.month, ' - first: ', event.first, ' - last: ', event.last);
    }

    onCalendarToggle(event: number): void {
        //console.log('onCalendarToggle(): Value: ', event);
    }

    onInputFocusBlur(event: IMyInputFocusBlur): void {
        //console.log('onInputFocusBlur(): Reason: ', event. reason, ' - Value: ', event.value);
    }

    getCopyOfOptions(): IMyDpOptions {
        return JSON.parse(JSON.stringify(this.myDatePickerNormalOptions));
    }
  ///////////////////////////////////////////////////
 
filter(){
	this.questionFilterDropdownsProvider =
			 new QuestionFilterDropdown({
            key: 'id_provider',
            label: 'Nombre del Proveedor:',
            value:  this.filterService.filter['id_provider']!=undefined? this.filterService.filter['id_provider']: -2,
            optionsKey: 'idProvider',
            optionsValue: 'fullNameProvider'
    });

	this.providerTypeService.getAll(this.providerTypeService.buildRequestOptionsFinder("name_provider_type", "s")).subscribe(params => { 
               this.questionFilterDropdownsProviderType.options = params['result']; 
		});
	this.questionFilterDropdownsProviderType  =
			new QuestionFilterDropdown({
                key: 'idProviderType',
                label: 'Tipo del proveedor:',
                value: this.filterService.filter['idProviderType']!=undefined? this.filterService.filter['idProviderType']: -1,
                optionsKey: 'idProviderType',
                optionsValue: 'nameProviderType',
				changed: this.changeProviderType.bind(this)
           });
    
	this.questionFilters = [this.questionFilterDropdownsProviderType, this.questionFilterDropdownsProvider];

	}

	changeProviderType(idProviderType: number)
	{
		this.filterService.changeFilter('idProviderType', idProviderType);
		delete this.filterService.filter['name_provider_type'];
		this.idProviderType = idProviderType;

		this.filter();
		var obj = JSON.parse('{ "idProvider":"-1", "fullNameProvider":" Buscar Todos"}');
        this.providerService.getAllSearch(this.providerService.buildRequestOptionsFinder("-fullName_Provider", "s","1",{"idProviderType":this.idProviderType+""})).subscribe(params => { 
		this.questionFilterDropdownsProvider.options = params['result'];
		this.questionFilterDropdownsProvider.options.push(obj);
		obj = this.questionFilterDropdownsProvider.options.reverse();
				
    }); 
	}


	list(page?: number)
	{
	
	//console.log("---------->"+this.filterService.filter['id_provider']+"<----------");
	if(!this.aux && this.filterService.filter['id_provider']!=-1)
	{
		this.aux=true;
		this.filterService.filter['id_provider']=-2;
		
	}
	else
	{
		if(this.filterService.filter['id_provider']==undefined) this.filterService.filter['id_provider']=-1;
	}
	//console.log("---------->"+this.filterService.filter['id_provider']+"<----------");
	this.filterService.filter["startDate"] = this.startDateValue;
	this.filterService.filter["endDate"]=this.endDateValue;
		
		this.invoiceService.getAllSearch(this.invoiceService.buildRequestOptionsFinder(
			this.tableService.sort,
			undefined,
			"-1",
			this.filterService.filter,
			{pageIndex: page, pageSize: this.tableService.pager.pageSize},
		)).subscribe(params => {
			this.items = params['result'];
			this.pager = params['pager'];
			this.tableService.pager.pageIndex = page;
			this.tableCmp.deselectAll();

			//console.log(params['result']);

			for(let item of this.items)
			{
				item.id=item.idInvoice;
				if(item.statusInvoice == '1')
				{
					item.statusInvoice="Abierta";
				}
				else
				{
					item.statusInvoice="Cerrada";
				}
		}
		});
		
	}

	read(item: Invoice){
		this.router.navigate(['./' + item.idInvoice], {relativeTo: this.activatedRoute});
	}

}
