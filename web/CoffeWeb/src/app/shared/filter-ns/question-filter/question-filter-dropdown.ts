import { QuestionFilterBase } from './question-filter-base';

export class QuestionFilterDropdown extends QuestionFilterBase<number> {
	controlType = 'dropdown';
	optionsKey: string;
	public options: {id: number, name: string}[] = [];

	constructor(options: {} = {}) {
		super(options);
		this.optionsKey = options['optionsKey'];
		this.options = options['options'] || [];
	}
}