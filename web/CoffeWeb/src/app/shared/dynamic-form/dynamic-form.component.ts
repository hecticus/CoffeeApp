import { Component, OnInit, Input, Output, EventEmitter  } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Fieldset } from './question/fieldset';
import { QuestionControlService } from './question-control.service';

@Component({
    moduleId: "module.id",
    selector: 'dynamic-form',
    templateUrl: 'dynamic-form.component.html',
    styleUrls: ['question.css'],
    providers: [QuestionControlService],
})
export class DynamicFormComponent implements OnInit {
    @Input() questions: Fieldset[] = [];
    @Output() submited = new EventEmitter();
    form: FormGroup;
    //payLoad = '';
    
    constructor(private qcs: QuestionControlService) {  }

    ngOnInit() {
        this.form = this.qcs.toFormGroup(this.questions);
    }

    onSubmited() {
        //this.payLoad = JSON.stringify(this.form.value);
        this.submited.emit(this.form);
    }
}