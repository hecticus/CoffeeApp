export class QuestionBase<T>{
    value: T;
    key: string;
    label: string;
    placeholder: string;
    required: boolean;
    controlType: string;
    hidden: boolean;
    proportion: number;

    constructor(options: {
        value?: T,
        key?: string,
        label?: string,
        placeholder?: string,
        required?: boolean,
        controlType?: string
        hidden?: boolean,
        proportion?: number
    } = {}) {
        this.value = options.value;
        this.key = options.key || '';
        this.label = options.label || '';
        this.placeholder = options.placeholder || '';
        this.required = !!options.required;
        this.controlType = options.controlType || '';
        this.hidden = options.hidden || false;
        this.proportion = options.proportion || 1;
    }
}