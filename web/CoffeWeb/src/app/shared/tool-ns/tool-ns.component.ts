import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ToolBase } from './tool/tool-base';

@Component({
	selector: 'tool-ns',
  	templateUrl: './tool-ns.component.html',
  	styleUrls: ['./tool-ns.component.css'],
})
export class ToolComponent {
	@Input() tools: ToolBase<any>[];
	@Input() toolsLeft: ToolBase<any>[];
}