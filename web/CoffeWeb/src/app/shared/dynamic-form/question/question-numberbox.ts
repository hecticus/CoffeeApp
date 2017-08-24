import { QuestionBase } from './question-base';

export class NumberboxQuestion extends QuestionBase<string> {
  controlType = 'numberbox';
  type: string = 'number';
  step: number;

  constructor(options: {} = {}) {
    super(options);
    this.step = options['step'] || '';
  }
}