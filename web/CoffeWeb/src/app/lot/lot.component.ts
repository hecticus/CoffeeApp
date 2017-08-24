import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TableService } from 'app/shared/table-ns/table-ns.service';
import { FilterService } from 'app/shared/filter-ns/filter-ns.service';

@Component({
 
  	template: '<router-outlet></router-outlet>',
  	providers: [
  		TableService,
  		FilterService
  	]
})
export class LotComponent {


}
