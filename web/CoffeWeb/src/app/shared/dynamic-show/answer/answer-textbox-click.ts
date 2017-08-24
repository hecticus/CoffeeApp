import { AnswerBase } from './answer-base';
import { Router, ActivatedRoute } from '@angular/router';

export class TextboxClickAnswer extends AnswerBase<string> {
	controlType = 'textbox-click';
	type: string;
	routerLink?: string;
	answers?: any[];

	constructor(options: {} = {}) {
		super(options);
		this.type = options['type'] || '';
		this.routerLink = options['routerLink'];
		this.answers = options['answers'];
	}
}