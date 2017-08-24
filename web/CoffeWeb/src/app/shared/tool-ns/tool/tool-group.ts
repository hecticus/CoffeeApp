import { ToolBase } from './tool-base';

export class GroupTool{
  	title: string;
  	tools: ToolBase<any>[];
  
	constructor(options: {
		title?: string,
		tools?: ToolBase<any>[]
	} = {}) {
		this.title = options.title || '';
		this.tools = options.tools;
	}
}