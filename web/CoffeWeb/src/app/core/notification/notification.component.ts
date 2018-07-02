// import { NotificationsService } from 'angular4-notifications';

import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { NotificationService } from './notification.service';

@Component({
	selector: 'app-notification',
	template: `
		<!--<simple-notifications
			[options]="options"
			(onCreate)="onCreate($event)"
			(onDestroy)="onDestroy($event)">
		</simple-notifications>

		<simple-notifications [options]="options"></simple-notifications>-->

		<!--<ng-template #example>
			<p>Simple example3</p>
		</ng-template>-->

		<div style="display:none;">
			<div #success i18n="@@noti-successful">Successful</div>
			<div #successContent i18n="@@noti-successful-content">Operation successful!</div>
		</div>

		<div style="display:none;">
			<div #error i18n="@@noti-error">Error</div>
			<div #errorContent i18n="@@noti-error-content">Error in the system, check with the administrator</div>
		</div>

		<div style="display:none;">
			<div #error400 i18n="@@noti-error400">Bad Request</div>
			<div #error400Content i18n="@@noti-error400-content">Invalid data</div>
		</div>

		<div style="display:none;">
			<div #error401 i18n="@@noti-error401">Unauthorized</div>
			<div #error401Content i18n="@@noti-error401-content">Invalid credentials</div>
		</div>

		<div style="display:none;">
			<div #error404 i18n="@@noti-error404">Not Found</div>
			<div #error404Content i18n="@@noti-error404-content">Resource not found</div>
		</div>

		<div style="display:none;">
			<div #error409 i18n="@@noti-error409">Conflict</div>
			<div #error409Content i18n="@@noti-error409-content">Check the dependencies</div>
		</div>

		<div style="display:none;">
			<div #duplicate i18n="@@noti-duplicated">Duplicated</div>
			<div #duplicateContent i18n="@@noti-duplicated-content">Register duplicated</div>
		</div>
	`
})
export class NotificationComponent implements AfterViewInit {
	// @ViewChild('example') example3: TemplateRef<any>;
	@ViewChild('success') success: ElementRef;
	@ViewChild('successContent') successContent: ElementRef;

	@ViewChild('error') error: ElementRef;
	@ViewChild('errorContent') errorContent: ElementRef;
	@ViewChild('error400') error400: ElementRef;
	@ViewChild('error400Content') error400Content: ElementRef;
	@ViewChild('error401') error401: ElementRef;
	@ViewChild('error401Content') error401Content: ElementRef;
	@ViewChild('error404') error404: ElementRef;
	@ViewChild('error404Content') error404Content: ElementRef;
	@ViewChild('error409') error409: ElementRef;
	@ViewChild('error409Content') error409Content: ElementRef;

	@ViewChild('duplicate') duplicate: ElementRef;
	@ViewChild('duplicateContent') duplicateContent: ElementRef;

	public options = {
		position: ['bottom', 'left'],
		timeOut: 1000,
		preventDuplicates: true
	};

	constructor(
		// private service: NotificationsService,
		private notificationService: NotificationService
	) {}

	ngAfterViewInit() {
		// console.log(this.example);

		this.notificationService.nsuccess.title = this.success.nativeElement.innerHTML;
		this.notificationService.nsuccess.content = this.successContent.nativeElement.innerHTML;

		this.notificationService.nerror.title = this.error.nativeElement.innerHTML;
		this.notificationService.nerror.content = this.errorContent.nativeElement.innerHTML;
		this.notificationService.nerror400.title = this.error400.nativeElement.innerHTML;
		this.notificationService.nerror400.content = this.error400Content.nativeElement.innerHTML;
		this.notificationService.nerror401.title = this.error401.nativeElement.innerHTML;
		this.notificationService.nerror401.content = this.error401Content.nativeElement.innerHTML;
		this.notificationService.nerror404.title = this.error404.nativeElement.innerHTML;
		this.notificationService.nerror404.content = this.error404Content.nativeElement.innerHTML;
		this.notificationService.nerror409.title = this.error409.nativeElement.innerHTML;
		this.notificationService.nerror409.content = this.error409Content.nativeElement.innerHTML;

		this.notificationService.nduplicate.title = this.duplicate.nativeElement.innerHTML;
		this.notificationService.nduplicate.content = this.duplicateContent.nativeElement.innerHTML;
	}

	/*onCreate(event) {
		console.log(event);
	}*/

	/*onDestroy(event) {
		console.log(event);
	}*/
}
