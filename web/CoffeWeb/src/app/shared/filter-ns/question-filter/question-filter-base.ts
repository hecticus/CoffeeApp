export class QuestionFilterBase<T>{
    value: T;
    key: string;
    label: string;
    placeholder: string;
    controlType: string;
    proportion: number;
    changed: Function;

    constructor(options: {
        value?: T,
        key?: string,
        label?: string,
        placeholder?: string,
        controlType?: string,
        proportion?: number,
        changed?: Function
    } = {}) {
        this.value = options.value;
        this.key = options.key || '';
        this.label = options.label || '';
        this.placeholder = options.placeholder || 'All';
        this.controlType = options.controlType || '';
        this.proportion = options.proportion || 1;
        this.changed = options.changed;
    }
}