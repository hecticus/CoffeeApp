import { AnswerBase } from './answer-base';

export class TextareaAnswer extends AnswerBase<string> {
	controlType = 'textarea';

	constructor(options: {} = {}) {
		super(options);
	}
}