import { Directive, Output, EventEmitter, HostListener } from '@angular/core';

@Directive({
	selector: '[videoListener]'
})
export class VideoListenerDirective {

	@Output() metaFiles = new EventEmitter();

	@HostListener('loadedmetadata', ['$event'])
	public onMetaData(evt) {

	}

	@HostListener('canplaythrough', ['$event']) 
	public onPlayThrought(evt) {

	}

}
