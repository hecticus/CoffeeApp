import { Component,  Input, Output, EventEmitter } from '@angular/core';

@Component({
	selector: 'bt-media',
	templateUrl: './button-media.component.html',
	styleUrls: ['../button.component.css']
})
export class ButtonMediaComponent {

	@Input() title: string;
	@Input() text: string;
	@Input() accept: string;
	@Output() changed = new EventEmitter();

	constructor() { }

	onClicked() {
		document.getElementById('files').click();
	}

	onChanged(event) {
		this.changed.emit(event);
	}
}
