import { Injectable } from '@angular/core';
import { TableService } from '../table-ns.service';

@Injectable()
export class TableExpandService extends TableService{
    expands = {};	//use for expanded rows per page

    getExpandsSize(page = this.pager.pageIndex){
    	if(page in this.expands)
			return Object.keys(this.expands[page]).length;
		return 0;
	}

    addExpand(item, page: number = this.pager.pageIndex){
    	if(!(page in this.expands))
    		this.expands[page] = {};
    	this.expands[page][item.id] = true;
	}

	addExpands(items: any[], page: number = this.pager.pageIndex){
		items.map(item => this.addExpand(item, page));
	}

	deleteExpand(key, page: number = this.pager.pageIndex){
		if(page in this.expands){
			delete this.expands[page][key]; 
			if(Object.keys(this.expands[page]).length === 0)
				delete this.expands[page];
		}
	}

	deleteExpands(keys: any[], page: number = this.pager.pageIndex){
		keys.map(key => this.deleteExpand(key, page));
	}

    toogleExpand(item, page: number = this.pager.pageIndex){
    	if(page in this.expands && item.id in this.expands[page])
			this.deleteExpand(item.id, page); 
		else
			this.addExpand(item, page);
	}

	emptyExpands(page: number = this.pager.pageIndex){
		delete this.expands[page];
	}
}