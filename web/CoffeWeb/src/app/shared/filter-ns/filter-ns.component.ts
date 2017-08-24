import { Component, Input, Output, EventEmitter } from '@angular/core';
import { QuestionFilterBase } from './question-filter/question-filter-base';
import { FilterService } from './filter-ns.service';

@Component({
    selector: 'filter-ns',
    templateUrl: 'filter-ns.component.html',
    styleUrls: ['filter-ns.component.css', '../dynamic-form/question.css'],
})
export class FilterComponent {
    @Input() questionFilters: QuestionFilterBase<any>[];
    @Output() list = new EventEmitter();

    constructor(
		private filterService: FilterService
	){}

    onChange(question: QuestionFilterBase<any>, value: any){
        if(question.changed == undefined){
           this.filterService.changeFilter(question.key, value);
        }else{
            question.changed(value);
        }
	}

    onSearch(){
        this.list.emit(0);
    }
}