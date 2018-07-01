import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Component({
	selector: 'app-validator',
	template: `
		<div *ngIf="control.hasError('noMatch')" class="errorMessage" i18n="@@validator-noMatches">No matches</div>

		<div *ngIf="control.hasError('required')" class="errorMessage" i18n="@@validator-required">Required</div>

		<!--<div *ngIf="control.hasError('pattern')" class="errorMessage" i18n="@@validator-invalid">Invalid</div>-->

		<div *ngIf="control.hasError('emailRegex')" class="errorMessage">
			Email invalid. The format should be example@dot.com
		</div>

		<div *ngIf="(control.dirty || control.touched) && control.hasError('integerRegex')" class="errorMessage">
			Number invalid, only integer numbers
		</div>

		<div *ngIf="(control.dirty || control.touched) && control.hasError('numberRegex')" class="errorMessage">
			Number invalid (use '.' to float numbers)
		</div>

		<div *ngIf="control.hasError('minlength')" class="errorMessage">
			Must have at least {{control.getError('maxlength').requiredLength}} characters
		</div>

		<div *ngIf="control.hasError('maxlength')" class="errorMessage">
			Must have maximum {{control.getError('maxlength').requiredLength}} characters
		</div>

		<div *ngIf="control.hasError('min')" class="errorMessage">
			Must not be less than {{control.getError('min')}}
		</div>

		<div *ngIf="control.hasError('max')" class="errorMessage">
			Must not be greater than {{control.getError('max')}}
		</div>
	`
})
export class ValidatorComponent {
	@Input() control: AbstractControl;
}
