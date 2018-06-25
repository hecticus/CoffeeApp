import { ClienteComponent } from './cliente/cliente.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ProofComponent } from './proof.component';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    ProofComponent,
    ClienteComponent,
  ],
  declarations: [
    ProofComponent,
    ClienteComponent,
  ]
})
export class ProofModule { }
