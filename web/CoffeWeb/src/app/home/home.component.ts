import { Component, OnInit  } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';

import { NotificationService } from '../common/notification/notification.service';

const styles = require('./home.component.css');
const template = require('./home.component.html');


@Component({
  selector: 'home',
  template: template,
  styles: [ styles ]
})
export class HomeComponent implements OnInit {

      constructor() { }
      ngOnInit() { }
   
}