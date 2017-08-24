import { QuestionBase } from './question-base';

export class Fieldset{
    legend: string;
    fields: QuestionBase<any>[][];

    constructor(options: {
        legend?: string,
        fields?: QuestionBase<any>[][]
    } = {}) {
        this.legend = options.legend;
        this.fields = options.fields;
    }
}