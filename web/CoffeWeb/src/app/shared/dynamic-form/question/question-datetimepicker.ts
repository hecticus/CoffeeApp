import { QuestionBase } from './question-base';
import { IMyOptions } from "../my-date-picker/interfaces/index";

// from /my-date-picker/interfaces/my-date-interface.ts
export interface IMyDate {
    year: number;
    month: number;
    day: number;
	time: string; //extra
}

export class DatetimePickerQuestion extends QuestionBase<string> {
	controlType = 'datetimepicker';
	//formatter: string = 'yyyy-mm-dd HH:mm:ss';
	formatter: string = 'yyyy-mm-dd';
	date: IMyDate;
	options: IMyOptions = {
        dateFormat: this.formatter,
    };

	constructor(options: {} = {}) {
		super(options);
		if(options['value'] != null){
			let date = new Date(options['value']);
			this.date = {
				year: date.getFullYear(), 
				month: date.getMonth() + 1, 
				day: date.getDate(),
				time: date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
			};
		}
	}
}