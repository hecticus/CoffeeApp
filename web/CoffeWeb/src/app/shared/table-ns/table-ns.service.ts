import { Injectable } from '@angular/core';

@Injectable()
export class TableService{
	selects = {};		//use for items selected
    pager: {pageIndex: number, pageSize: number} = {pageIndex: 0, pageSize: 3};
   	sort: string;

   	public TableService(){
   		this.pager.pageIndex = 0;
   		this.pager.pageSize = 3;
   	}

   	setSort(key){
   		if(this.sort === undefined)
			this.sort = key;
   	}

    refreshPageIndexAfterRemove(itemsRemoved: number, pager) {
        if(	pager.totalEntitiesPage === itemsRemoved && 
        	this.pager.pageIndex > 0 && 
        	this.pager.pageIndex === pager.pages-1)
			return --this.pager.pageIndex;
        return this.pager.pageIndex;
	}

	getItemValue(item, columnKey){
		let keySplits = columnKey.split('.');
      	let innerObj = item;

		for(var i = 0; i < keySplits.length-1; ++i){
			if(innerObj[keySplits[i]] == undefined)
				break;
			innerObj = innerObj[keySplits[i]];
		}
		
		return innerObj[keySplits[keySplits.length-1]];
	}

	getSelectKeys(){
		let keys: any[] = [];
		for(var key in this.selects)
			keys.push(this.selects[key].id);
		return keys;
	}

	getSelectsSize(){
		return Object.keys(this.selects).length;
	}

	addSelect(item){
		this.selects[item.id] = item;
	}

	addSelects(items){
		items.map(item => this.addSelect(item));
	}

	deleteSelect(key){
		delete this.selects[key]; 
	}

	deleteSelects(keys: any[]){
		keys.map(key => this.deleteSelect(key));
	}

	toogleSelect(item){
		if(item.id in this.selects)
			this.deleteSelect(item.id);
		else
			this.addSelect(item);
	}

	emptySelects(){
		this.selects = {};
	}
}