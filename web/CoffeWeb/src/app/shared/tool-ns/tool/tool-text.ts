import { ToolBase } from './tool-base';

export class TextTool extends ToolBase<any> {
	controlType = 'buttonText';
  	text: string;
  
	constructor(options: {} = {}) {
		super(options);
		this.text = options['text'] || '';
	}
}