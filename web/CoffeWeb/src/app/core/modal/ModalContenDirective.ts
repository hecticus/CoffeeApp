import { TemplateRef, ElementRef, Directive } from "@angular/core";

@Directive({
	selector: '[modalContentDirective]'
})
export class ModalContentDirective {

	constructor(public template: TemplateRef<any>, public elementRef: ElementRef) {
	}
}
