import { Component, Input } from '@angular/core';
import { FieldsetAnswer } from './answer/fieldset';

@Component({
	moduleId: "module.id",
	selector: "dynamic-show",
	templateUrl: "dynamic-show.component.html",
	styleUrls: ["answer.css"]
})
export class DynamicShowComponent {
	@Input() answers: FieldsetAnswer[] = [];
}