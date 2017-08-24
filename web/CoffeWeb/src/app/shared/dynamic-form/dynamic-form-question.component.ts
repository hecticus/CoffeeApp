import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { QuestionBase } from './question/question-base';
import { IMyOptions, IMyDateModel } from "./my-date-picker/interfaces/index";

@Component({
	moduleId: "module.id",
	selector: 'df-question',
	templateUrl: 'dynamic-form-question.component.html',
  	styleUrls: ['question.css'],
})
export class DynamicFormQuestionComponent {
	@Input() question: QuestionBase<any>;
	@Input() form: FormGroup;
	//control;

	ngOnInit() {
        //this.control = this.form.controls[this.question.key];
    }

    onDateChanged(event: IMyDateModel) {
        // event properties are: event.date, event.jsdate, event.formatted and event.epoc
        this.form.controls[this.question.key].patchValue(event.formatted);
    }

    onDateTimeChanged(event: IMyDateModel) {
        // event properties are: event.date, event.jsdate, event.formatted and event.epoc
        this.form.controls[this.question.key].patchValue(event.formatted + " 00:00:01");
    }

	get isValid() { return this.form.controls[this.question.key].valid; }
}