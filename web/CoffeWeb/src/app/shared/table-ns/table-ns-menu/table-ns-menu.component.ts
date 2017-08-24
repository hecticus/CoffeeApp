import { Component, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { ContextMenuService } from 'angular2-contextmenu/src/contextMenu.service';
import { TableService } from '../table-ns.service';
import { TableColumn } from '../table-ns-column';

@Component({
	selector: 'table-ns-menu',
  	templateUrl: './table-ns-menu.component.html',
  	styleUrls: ['../table-ns.component.css'],
  	providers: [ContextMenuService],
})
export class TableMenuComponent {
	@Input() title: string;
	@Input() items: any[];
	@Input() cols: TableColumn[];
	@Input() actions2: any[];
	@Input() actions: any[];
	@Output() read = new EventEmitter();
	@Output() list = new EventEmitter();

	constructor(
		private contextMenuService: ContextMenuService, 
		private tableService: TableService
	){}

	selectToogle(item){
		this.tableService.toogleSelect(item);
	}

	selectOne(item){
		this.tableService.emptySelects();
		this.tableService.addSelect(item);
	}

	selectAllToogle($event){
		if($event.target.checked)
			this.tableService.addSelects(this.items);
		else
			this.tableService.emptySelects();
	}

	deselect(id: number){
		this.tableService.deleteSelect(id);
	}

	deselects(ids: number[]){
		ids.map(id => this.tableService.deleteSelect(id));
	}

	deselectAll(){
		this.tableService.emptySelects();
	}

	remove(id: number){
		this.deselect(id);
	}

	removes(ids: number[]){
		this.deselects(ids);
	}

	onRead(item){
		this.read.emit(item);
	}

	sortAsc(key: string){
		this.tableService.sort = key;
		this.list.emit(this.tableService.pager.pageIndex);
	}

	sortDesc(key: string){
		this.tableService.sort = '-' + key;
		this.list.emit(this.tableService.pager.pageIndex);
	}

	public onContextMenu($event: MouseEvent, item: any): void {
    	this.contextMenuService.show.next({
	      	actions: this.actions,
			event: $event,
			item: item
	    });
	    $event.preventDefault();
	}

	public onContextMenu2($event: MouseEvent, item: any): void {
		let e = {
			target: $event.target,
			buttons: $event.buttons,
			clientX: $event.clientX - 200, //width menucontext
			clientY: $event.clientY,
			layerX: $event.layerX,
			layerY: $event.layerY
		}
    	this.contextMenuService.show.next({
	      	actions: this.actions,
			event: <MouseEvent>e,
			item: item
	    });
	    $event.preventDefault();
	    this.selectOne(item);
	}
}