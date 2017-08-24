import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
	selector: 'bt-icon',
  	templateUrl: './button-icon.component.html',
  	styleUrls: ['../button.component.css'],
})
export class ButtonIconComponent {
	@Input() title: string;
	@Input() type: string;
	@Input() icon: string;
    @Output() clicked = new EventEmitter();

  	onClicked($event){
		this.clicked.emit($event);
	}
}