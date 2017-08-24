import { AnswerBase } from './answer-base';

export class TextboxAnswer extends AnswerBase<string> {
	controlType = 'textbox';
	type: string;

	constructor(options: {} = {}) {
		super(options);
		this.type = options['type'] || '';
	}
}