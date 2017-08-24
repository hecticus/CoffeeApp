import { AnswerBase } from './answer-base';

export class FieldsetAnswer{
    legend: string;
    fields: AnswerBase<any>[][];

    constructor(options: {
        legend?: string,
        fields?: AnswerBase<any>[][]
    } = {}) {
        this.legend = options.legend;
        this.fields = options.fields;
    }
}