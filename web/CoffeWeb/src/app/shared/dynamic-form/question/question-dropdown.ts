import { QuestionBase } from './question-base';

export class DropdownQuestion extends QuestionBase<string> {
	controlType = 'dropdown';
	optionsKey: string;
	options: {id: number, name: string}[] = [];

	constructor(options: {} = {}) {
		super(options);
		this.optionsKey = options['optionsKey'];
		this.options = options['options'] || [];
	}
}