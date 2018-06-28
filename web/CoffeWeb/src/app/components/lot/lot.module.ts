import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LotListComponent } from './lot-list.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    LotListComponent,
  ],
  exports: [
    LotListComponent,
  ]
})
export class LotModule { }
