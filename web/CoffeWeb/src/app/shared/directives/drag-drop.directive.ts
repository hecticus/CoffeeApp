import { Directive, Output, EventEmitter, HostBinding, HostListener } from '@angular/core';

@Directive({
	selector: '[dragAndDrop]'
})
export class DragDropDirective {

	@Output()
	filesChange: EventEmitter<FileList> = new EventEmitter();
	@HostBinding('style.background') private background;

	@HostListener('dragover', ['$event']) public onDragOver(evt) {
		evt.preventDefault();
		evt.stopPropagation();
		this.background = '#999';
	}

	@HostListener('drop', ['$event']) public onDrop(evt) {
		evt.preventDefault();
		evt.stopPropagation();
		let files: FileList = evt.dataTransfer.files;
		if (files.length > 0) {
			this.background = 'rgba(102, 102, 102, 0.1)';
			this.filesChange.emit(files);
		}
	}

}
