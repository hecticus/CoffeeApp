import { Component, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { TableColumn } from '../table-ns-column';
import { TableService } from '../table-ns.service';

@Component({
	selector: 'table-ns-basic',
  	templateUrl: './table-ns-basic.component.html',
  	styleUrls: ['../table-ns.component.css']
})
export class TableBasicComponent {
	@Input() title: string;
	@Input() items: any[];
	@Input() cols: TableColumn[];
	@Input() pager: {};
	@Output() read = new EventEmitter();
	@Output() list = new EventEmitter();
	@ViewChild('pagerCmp') pagerCmp;

	constructor(
		private tableService: TableService
	){}

	/*ngAfterViewInit() {
		// if exist detail each item have pointer cursor
		if(this.read.observers.length > 0){
			this.tableService.addPointer();
		}
	}*/

	onList(page){
		this.list.emit(page);
	}

	onRead(item){
		this.read.emit(item);
	}
}