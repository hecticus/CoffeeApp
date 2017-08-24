import { AnswerBase } from './answer-base';


export class DatePickerAnswer extends AnswerBase<string> {
	controlType = 'datepicker';
	formatter: string = 'yyyy-MM-dd';

	constructor(options: {} = {}) {
		super(options);
	}
}