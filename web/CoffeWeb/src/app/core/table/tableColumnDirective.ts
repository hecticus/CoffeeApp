import { Directive, ElementRef, Input, TemplateRef } from '@angular/core';

@Directive({
		// tslint:disable-next-line:directive-selector
		selector: '[tableColumn]'
})
export class TableColumnDirective {
		@Input() propertyKey: string;
		@Input() grow = 1;

		constructor(public template: TemplateRef<any>, public elementRef: ElementRef) {
		}
}
