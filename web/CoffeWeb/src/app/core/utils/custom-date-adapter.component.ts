import { Injectable } from '@angular/core';
// Custom DateAdapter
import {NgModule} from '@angular/core';
import { DateAdapter, NativeDateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatDateFormats,
	MatDatepickerModule, MatNativeDateModule } from '@angular/material';

@Injectable({
providedIn: 'root',
})
// extend NativeDateAdapter's format method to specify the date format.
export class CustomDateAdapter extends NativeDateAdapter {
format(date: Date, displayFormat: Object): string {
	if (displayFormat === 'input') {
		const day = date.getUTCDate();
		const month = date.getUTCMonth() + 1;
		const year = date.getFullYear();
		// Return the format as per your requirement
		return `${year}-${month}-${day}`;
	} else {
		return date.toDateString();
	}
}

// If required extend other NativeDateAdapter methods.
}

export const MY_DATE_FORMATS = {
parse: {
	dateInput: {month: 'short', year: 'numeric', day: 'numeric'}
},
display: {
	dateInput: 'input',
	monthYearLabel: {year: 'numeric', month: 'short'},
	dateA11yLabel: {year: 'numeric', month: 'long', day: 'numeric'},
	monthYearA11yLabel: {year: 'numeric', month: 'long'},
}
};

@NgModule({
declarations: [],
imports: [],
exports: [MatDatepickerModule, MatNativeDateModule],
providers: [
	{
		provide: DateAdapter, useClass: CustomDateAdapter
	},
	{
		provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS
	}
]
})

export class DatePickerModule {

}
