export class ToolBase<T>{
    title: string;
    clicked: Function;
    controlType: string;
    type: string;

    constructor(options: {
        title?: string,
        clicked?: Function,
        controlType?: string,
        type?: string
    } = {}) {
        this.title = options.title || '';
		this.clicked = options.clicked || function(){};
        this.controlType = options.controlType || '';
        this.type = options.type || 'button';
    }
}