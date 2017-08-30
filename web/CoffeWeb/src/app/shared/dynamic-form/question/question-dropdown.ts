import { QuestionBase } from './question-base';

export class DropdownQuestion extends QuestionBase<string> {
	controlType = 'dropdown';
	optionsKey: number;
	optionsValue: string;
	options: {id: number, name: string}[] = [];

	constructor(options: {} = {}) {
		super(options);
		this.optionsValue = options['optionsValue'];
		this.optionsKey = options['optionsKey'];
		this.options = options['options'] || [];
	}
}