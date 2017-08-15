import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
	selector: 'bt-icon-text',
  	templateUrl: './button-icon-text.component.html',
  	styleUrls: ['../button.component.css'],
})
export class ButtonIconTextComponent {
	@Input() title: string;
	@Input() type: string;
	@Input() icon: string;
	@Input() text: string;
    @Output() clicked = new EventEmitter();

  	onClicked($event){
		this.clicked.emit($event);
	}
}