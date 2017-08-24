import { Component, Input } from '@angular/core';
import { FieldsetAnswer } from '../answer/fieldset';

@Component({
	moduleId: "module.id",
	selector: "dynamic-show-small",
	templateUrl: "../dynamic-show.component.html",
	styleUrls: ["../answer.css", "dynamic-show-small.component.css"]
})
export class DynamicShowSmallComponent {
	@Input() answers: FieldsetAnswer[] = [];
}