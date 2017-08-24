import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { PagerService } from './pagination.service'

// https://github.com/cornflourblue/angular2-pagination-example
@Component({
	selector: 'pagination',
	templateUrl: './pagination.component.html',
	styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, OnChanges {
	@Input() totalItems: number;
	@Input() initPage: number;
	@Input() pageSize: number;
	@Output() paginated = new EventEmitter<number>(); 
	pager: any = {};

	constructor(private pagerService: PagerService){}

	ngOnInit() {
		this.pager = this.pagerService.getPager(this.totalItems, this.initPage, this.pageSize);
	}

	ngOnChanges(...args: any[]) {
        //console.log('onChange fired');
        //console.log('changing', args);
        this.pager = this.pagerService.getPager(this.totalItems, this.initPage, this.pageSize);
    }

	setPage(page: number) {
        if(page < 1){
            page = 1;
        }else if(page > this.pager.totalPages){
        	page = this.pager.totalPages
    	}
     
        // get pager object from service
        //this.pager = this.pagerService.getPager(this.totalItems, page, this.pageSize);

        // get current page of items
        //this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
        this.paginated.emit(page - 1);
	}
}