import { Component, OnInit  } from '@angular/core';
import { HttpClient, HttpParams, HttpClientModule } from '@angular/common/http';

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
