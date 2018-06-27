import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';

import { LotService } from './lot.service';
import { Lot } from '../../core/models/lot';

@Component({
      templateUrl: '../common/crud/create/create.component.html'
})


export class LotCreateComponent {
    private lot: Lot;
    form: FormGroup;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location,
        private lotService: LotService,
    ) {}

  ngOnInit() {
  }

}
