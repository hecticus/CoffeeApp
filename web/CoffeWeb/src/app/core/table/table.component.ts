import { TableColumnDirective } from './tableColumnDirective';
import { TablaService } from './tabla.service';
import { Component, OnInit, QueryList, Input, Output, EventEmitter, ContentChildren } from '@angular/core';
import {  ContextMenuService } from 'ngx-contextmenu/lib/contextMenu.service';

@Component({
	selector: 'app-table',
	templateUrl: './table.component.html',
	styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

	@Input() items: any[];
	@Output() read = new EventEmitter();
	@Output() list = new EventEmitter();
	@ContentChildren(TableColumnDirective) public columns: QueryList<TableColumnDirective>;

	constructor(
	// private contextMenuService: ContextMenuService,
	public tableService: TablaService
	) {}

	/*ngAfterViewInit() {
	this.columns.forEach(column => this.tableService.sort = column.propertyKey);
	}*/
	ngOnInit() {
	}

	onRead(item) {
	this.read.emit(item);
	}

	sortAsc(key: string) {
	this.tableService.sort = key;
	this.list.emit(this.tableService.pager.pageIndex);
	}

	sortDesc(key: string) {
	this.tableService.sort = '-' + key;
	this.list.emit(this.tableService.pager.pageIndex);
	}

	// public onContextMenu($event: MouseEvent, item: any): void {
	//   this.contextMenuService.show.next({
	//     // Optional - if unspecified, all context menu components will open
	//     // contextMenu: this.contextMenu,
	//     event: $event,
	//     item: item,
	//   });
	//   $event.preventDefault();
	//   $event.stopPropagation();
	// }

}
