import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Fieldset } from './question/fieldset';

@Injectable()
export class QuestionControlService {
	constructor() { }

	toFormGroup(fielssets: Fieldset[]) {
		let group: any = {};
		//let validators = [];

		fielssets.forEach(fielsset => {
			fielsset.fields.forEach(field => {
				field.forEach(question => {
			  		group[question.key] = question.required ? 
			  			new FormControl(question.value, Validators.required): 
			  			new FormControl(question.value);
			    });
			});

		    /*if(question.required)
		    	validators.push(Validators.required);
		    if(question.pattern != undefined){
		    	console.log(question.pattern);
		    	validators.push(Validators.pattern(question.pattern));
		    }
		                          		
		   	group[question.key] = new FormControl(question.value || '', validators);*/
		});
		return new FormGroup(group);
	}
}