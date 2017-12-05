import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TableService } from '../shared/table-ns/table-ns.service';
import { FilterService } from '../shared/filter-ns/filter-ns.service';

@Component({

  	template: '<router-outlet align="left" ></router-outlet>',
  	providers: [
  		TableService,
  		FilterService
  	]
})
export class ProviderComponent {


}
