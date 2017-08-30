import { Component, OnInit, Input } from '@angular/core';
import { NotificationsService, SimpleNotificationsComponent } from 'angular2-notifications';

@Component({
  selector: 'notification',
  template: '<simple-notifications [options]="options" (onCreate)="onCreate($event)" (onDestroy)="onDestroy($event)"></simple-notifications>'  

})
export class NotificationComponent implements OnInit {

  @Input() content: JSON;

  constructor(private service: NotificationsService) { }

  ngOnInit() {
    if (this.content !== undefined) this.createNotification();        
  }

  public options = {
    position: ["bottom", "right"],
    timeOut: 4000, 
    preventDuplicates: true,
    showProgressBar: true,
    pauseOnHover: false,
    clickToClose: true       
  }

  createNotification() {
  	switch (this.content["type"]) {
			case 'success':
				this.service.success("Notification", this.content["message"]);
				break;
			case 'alert':
				this.service.alert("Notification", this.content["message"]);
        break;
      case 'error':
        this.service.error("Error", this.content["message"]);
			  break;
  	}
  }  

  onCreate(event) { /*//console.log(event);*/ }

  onDestroy(event) { /*//console.log(event);*/ }
}
