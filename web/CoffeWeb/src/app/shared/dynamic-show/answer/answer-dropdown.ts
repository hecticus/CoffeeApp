import { AnswerBase } from './answer-base';

export class DropdownAnswer extends AnswerBase<string> {
	controlType = 'dropdown';
	options: {id: string, name: string}[] = [];

	constructor(options: {} = {}) {
		super(options);
		this.options = options['options'] || [];
	}
}