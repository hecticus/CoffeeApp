import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
	selector: 'bt-text',
	templateUrl: './button-text.component.html',
	styleUrls: ['../button.component.css'],
})
export class ButtonTextComponent {
	@Input() title: string;
	@Input() type: string;
	@Input() text: string;
	@Output() clicked = new EventEmitter();

	onClicked($event){
		this.clicked.emit($event);
	}
}