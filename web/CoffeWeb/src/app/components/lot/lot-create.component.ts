import { Farm } from './../../core/models/farm';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup } from '@angular/forms';

import { LotService } from './lot.service';
import { Lot } from '../../core/models/lot';

// @Component({
//       template: `
//       <h3 class="title"> Create Lot</h3>

//       <form *ngIf="form" [formGroup]="form" (ngSubmit)="create()">
//           <fieldset>
//               <legend >Store data</legend>

//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="name">Name</label>
//                       <input formControlName="name" type="text" autocomplete="off">
//                       <validator [control]="form.controls.name"></validator>
//                   </div>
//               </div>
//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="company" i18n="@@company">Company</label>
//                       <dropdown-ns
//                           [defaultPropertyKey]="form.value.company?.id"
//                           (changed)="changeCompany($event)">
//                           <ng-container *ngFor="let object of companies">
//                               <ng-template dropdownOption [propertyKey]="object.id" [propertyValue]="object">{{object.name}}</ng-template>
//                           </ng-container>
//                       </dropdown-ns>
//                       <validator [control]="form.controls.company"></validator>
//                   </div>
//               </div>
//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="name" i18n="@@contactName">contact name</label>
//                       <input formControlName="contactName" type="text" autocomplete="off">
//                       <validator [control]="form.controls.contactName"></validator>
//                   </div>
//                   <div class="field">
//                       <label for="name" i18n="@@contactPhone">Contact phone</label>
//                       <input formControlName="contactPhone" type="text" autocomplete="off">
//                       <validator [control]="form.controls.contactPhone"></validator>
//                   </div>
//               </div>
//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="maintenenceFrecuency" i18n="@@maintenenceFrecuency">Maintenence frecuency</label>
//                       <input formControlName="maintenenceFrecuency" type="text" autocomplete="off">
//                       <validator [control]="form.controls.maintenenceFrecuency"></validator>
//                   </div>
//               </div>
//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="description" i18n="@@description">Description</label>
//                       <textarea formControlName="description"></textarea>
//                   </div>
//               </div>
//           </fieldset>

//           <fieldset formGroupName="contact">
//               <legend i18n="@@data-contact">Contact data</legend>

//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="phone" i18n="@@phone">Phone</label>
//                       <input formControlName="phone" type="text" autocomplete="off">
//                       <validator [control]="form.controls.contact.controls.phone"></validator>
//                   </div>
//                   <div class="field">
//                       <label for="phone2" i18n="@@phone2">Second phone</label>
//                       <input formControlName="phone2" type="text" autocomplete="off">
//                       <validator [control]="form.controls.contact.controls.phone2"></validator>
//                   </div>
//               </div>
//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="latitude" i18n="@@latitude">Latitude</label>
//                       <input formControlName="latitude" type="text" autocomplete="off">
//                       <validator [control]="form.controls.contact.controls.latitude"></validator>
//                   </div>
//                   <div class="field">
//                       <label for="longitude" i18n="@@longitude">Longitude</label>
//                       <input formControlName="longitude" type="text" autocomplete="off">
//                       <validator [control]="form.controls.contact.controls.longitude"></validator>
//                   </div>
//               </div>
//               <div class="wrap-fields">
//                   <div class="field">
//                       <label for="address" i18n="@@address">Address</label>
//                       <textarea formControlName="address"></textarea>
//                   </div>
//               </div>
//           </fieldset>

//           <div class="options row">
//               <button class="btn-text" type="submit" [disabled]="!form.valid">
//                   <div class="text" i18n="@@option-save">Save</div>
//               </button>
//           </div>
//       </form>
//   `'
// })


export class LotCreateComponent implements OnInit  {
    lot: Lot;
    form: FormGroup;
    farm: Farm;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location,
        private lotService: LotService,
    ) {}

    ngOnInit() {
    }

    create() {
        this.lotService.create(<Lot> this.form.value).subscribe

  }
  //   create() {
	// 	this.storeService.create(<Store> this.form.value).subscribe(store => {
	// 		this.notificationService.sucessInsert(store.name);
	// 		this.location.back();
	// 	}, err => this.notificationService.error(err));
	// }
}
