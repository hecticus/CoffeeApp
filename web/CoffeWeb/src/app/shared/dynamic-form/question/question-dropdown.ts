import { QuestionBase } from './question-base';

export class DropdownQuestion extends QuestionBase<string> {
	controlType = 'dropdown';
	optionsKey: number;
	optionsValue: string;
	options: {id: number, name: string}[] = [];

	constructor(options: {} = {}) {
		super(options);
		this.optionsKey = options['optionsKey'];
		this.optionsValue = options['optionsValue'];
		this.options = options['options'] || [];

		console.log(this.optionsKey+"------2-----"+this.optionsValue);
	}
}