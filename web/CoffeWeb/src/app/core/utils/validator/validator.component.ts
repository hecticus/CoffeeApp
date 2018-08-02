import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Component({
	selector: 'app-validator',
	template: `
		<div *ngIf="control.hasError('noMatch')" class="errorMessage">No Coincide</div>

		<div *ngIf="control.hasError('required')" class="errorMessage">Requerido</div>

		<!--<div *ngIf="control.hasError('pattern')" class="errorMessage" i18n="@@validator-invalid">Invalid</div>-->

		<div *ngIf="control.hasError('emailRegex')" class="errorMessage">
			Correo Invalido. El formato debe ser  example@dot.com
		</div>

		<div *ngIf="(control.dirty || control.touched) && control.hasError('integerRegex')" class="errorMessage">
			Numero invalido, Solo numeros enteros
		</div>

		<div *ngIf="(control.dirty || control.touched) && control.hasError('numberRegex')" class="errorMessage">
			Numbero invalidos (use '.' para numeros enteros)
		</div>

		<div *ngIf="control.hasError('minlength')" class="errorMessage">
			Debe tener al menos {{control.getError('maxlength').requiredLength}} caracteres
		</div>

		<div *ngIf="control.hasError('maxlength')" class="errorMessage">
			Debe tener maximo {{control.getError('maxlength').requiredLength}} caracteres
		</div>

		<div *ngIf="control.hasError('min')" class="errorMessage">
			No debe tener menos de {{control.getError('min')}}
		</div>

		<div *ngIf="control.hasError('max')" class="errorMessage">
			No debe tener mas de {{control.getError('max')}}
		</div>
	`
})
export class ValidatorComponent {
	@Input() control: AbstractControl;
}
