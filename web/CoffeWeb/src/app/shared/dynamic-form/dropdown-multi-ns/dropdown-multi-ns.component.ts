import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'dropdown-multi-ns',
    templateUrl: 'dropdown-multi-ns.component.html',
    styleUrls: ['../question.css', 'dropdown-multi-ns.component.css']
})
export class DropdownMultiComponent implements OnInit {
    active: boolean = false;
    options: any[] = [{id: 1, name: "a"}, {id: 1, name: "ab"}, {id: 1, name: "abc", }];

    constructor() {  }

    ngOnInit() {
    }

    toogleSelect(){
        this.active = !this.active;
    }

    toogleSelectOption(){

	}
}
