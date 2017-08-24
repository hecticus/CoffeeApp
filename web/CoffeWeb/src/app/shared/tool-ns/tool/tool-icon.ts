import { ToolBase } from './tool-base';

export class IconTool extends ToolBase<any> {
	controlType = 'buttonIcon';
  	icon: string;

	constructor(options: {} = {}) {
		super(options);
		this.icon = options['icon'];
	}
}