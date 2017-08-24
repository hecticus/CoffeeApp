export class AnswerBase<T>{
    value: T;
    key: string;
    label: string;
    controlType: string;
    proportion: number;

    constructor(options: {
        value?: T,
        key?: string,
        label?: string,
        controlType?: string,
        proportion?: number
    } = {}) {
        this.value = options.value;
        this.key = options.key || '';
        this.label = options.label || '';
        this.controlType = options.controlType || '';
        this.proportion = options.proportion || 0;
    }
}