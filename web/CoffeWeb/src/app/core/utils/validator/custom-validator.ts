import { FormControl, Validators, ValidatorFn } from '@angular/forms';

// reference: http://devnotes.fabioluiz.de/angular-2-forms-validation
// reference: https://blog.thoughtram.io/angular/2016/03/14/custom-validators-in-angular-2.html
export class CustomValidators {

	/*static matchGroup(a, b) {
		return (AC: AbstractControl) => {
			if (AC.get(a) && AC.get(b)) {
				let main = AC.get(a).value;
				let Second phone = AC.get(b).value;
				if (main && main !== '' && main !== Second phone) {
					AC.get(b).setErrors( {noMatch: true} );
				} else {
					return null;
				}
			}
		};
	}*/

	static match(name: string): ValidatorFn {
		return (control: FormControl): {[key: string]: any} => {
			if (control && control.parent) {
				let a = control.parent.get(name);
				if (a.value && a.value !== control.value) {
					return {
						noMatch: {
							valid: false
						}
					};
				}
			}
		};
	}

	static emailRegex(control: FormControl) {
		const EMAIL_REGEXP = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/i;

		return ( Validators.required(control) != null || EMAIL_REGEXP.test(control.value) ) ? null : {
			emailRegex: {
				valid: false
			}
		};
	}

	static integerRegex(control: FormControl) {
		const INTEGER_REGEXP = /^\d+$/;

		return ( Validators.required(control) != null || INTEGER_REGEXP.test(control.value) ) ? null : {
			integerRegex: {
				valid: false
			}
		};
	}

	static numberRegex(control: FormControl) {
		const NUMBER_REGEXP = /^(-)?[0-9]+(\.[0-9]+)?$/;

		return ( Validators.required(control) != null || NUMBER_REGEXP.test(control.value) ) ? null : {
			numberRegex: {
				valid: false
			}
		};
	}

	static max(max: number): ValidatorFn {
		return (control: FormControl): {[key: string]: any} => {
			if (isNaN(control.value) || Validators.required(control) != null) {
				return null;
			}

			let val: number = control.value;
			return val <= max ? null : {
				max: {
					valid: false,
					maxValue: max
				}
			};
		};
	}

	static min(min: number): ValidatorFn {
		return (control: FormControl): {[key: string]: any} => {
			if (isNaN(control.value) || Validators.required(control) != null) {
				return null;
			}

			let val: number = control.value;
			return val >= min ? null : {
				min: {
					valid: false,
					minValue: min
				}
			};
		};
	}
}
