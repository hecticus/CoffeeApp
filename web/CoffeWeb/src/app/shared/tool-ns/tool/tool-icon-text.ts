import { ToolBase } from './tool-base';

export class IconTextTool extends ToolBase<any> {
	controlType = 'buttonIconText';
  	icon: string;
  	text: string;
  
	constructor(options: {} = {}) {
		super(options);
		this.icon = options['icon'];
		this.text = options['text'] || '';
	}
}