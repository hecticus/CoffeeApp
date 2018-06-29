import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LotListComponent } from './lot-list.component';

@NgModule({
  imports: [
    CommonModule,
    MatTableModule,
  ],
  declarations: [
    LotListComponent,
  ],
  exports: [
    LotListComponent,
  ]
})
export class LotModule { }
