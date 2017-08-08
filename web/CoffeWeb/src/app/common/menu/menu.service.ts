import { Injectable } from '@angular/core';

@Injectable()
export class MenuService{
	items: any[];
	itemSelected;

	selectMenuItem(item) {
	    if(this.itemSelected != undefined)
	    	this.itemSelected.selected = false;
	    this.itemSelected = item;
	    this.itemSelected.selected = true;
	}
}