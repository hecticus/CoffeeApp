import { Component, Input, ViewChild } from '@angular/core';
import { FieldsetAnswer } from '../answer/fieldset';

@Component({
	selector: "textbox-click",
	templateUrl: "textbox-click.component.html",
	styleUrls: ["../answer.css"]
})
export class TextboxClickComponent {
	@ViewChild('btToogleComp') btToogleComponent;
	@Input() answers: FieldsetAnswer[] = [];
	@Input() answer: any;
	hidden: boolean = true;

	onClicked(){
		this.hidden = !this.btToogleComponent.toogle;
	}
}