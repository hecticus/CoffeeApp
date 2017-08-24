 import { QuestionFilterBase } from './question-filter-base';

export class QuestionFilterTextbox extends QuestionFilterBase<string> {
	controlType = 'textbox';
	type: string;

	constructor(options: {} = {}) {
		super(options);
	}
}