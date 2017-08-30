import { QuestionFilterBase } from './question-filter-base';

export class QuestionFilterDropdown extends QuestionFilterBase<number> {
	controlType = 'dropdown';
	optionsKey: number;
	optionsValue: string;
	public options: {id: number, name: string}[] = [];

	constructor(options: {} = {}) {
		super(options);
		this.optionsValue = options['optionsValue'];
		this.optionsKey = options['optionsKey'];
		this.options = options['options'] || [];
	}
}