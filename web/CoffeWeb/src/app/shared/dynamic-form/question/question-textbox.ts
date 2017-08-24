import { QuestionBase } from './question-base';

export class TextboxQuestion extends QuestionBase<string> {
	controlType = 'textbox';
    //pattern: string;
	type: string;

	constructor(options: {} = {}) {
		super(options);
		this.type = options['type'] || '';
        //this.pattern = options.pattern;
	}
}