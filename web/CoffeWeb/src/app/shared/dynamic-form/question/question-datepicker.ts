import { QuestionBase } from './question-base';
import { IMyOptions, IMyDate } from "../my-date-picker/interfaces/index";

export class DatePickerQuestion extends QuestionBase<string> {
	controlType = 'datepicker';
	formatter: string = 'yyyy-mm-dd';
	date: IMyDate;
	options: IMyOptions = {
        dateFormat: this.formatter,
    };;

	constructor(options: {} = {}) {
		super(options);
		if(options['value'] != null){
			let date = new Date(options['value']);
			this.date = {
				year: date.getFullYear(), 
				month: date.getMonth(), 
				day: date.getDate()
			};
		}
	}
}