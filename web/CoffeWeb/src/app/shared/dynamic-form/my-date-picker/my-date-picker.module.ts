import { CommonModule } from '@angular/common'; 
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { MyDatePicker } from "./my-date-picker.component";
import { FocusDirective } from "./directives/my-date-picker.focus.directive";

@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule],
    declarations: [MyDatePicker, FocusDirective],
    exports: [MyDatePicker, FocusDirective]
})
export class MyDatePickerModule {
}
//https://github.com/kekeh/mydatepicker