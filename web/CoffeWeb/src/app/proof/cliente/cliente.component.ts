import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  @Input()
  nombre = 'DesarrolloWeb.com';
  @Input()
  cif: string;
  @Input()
  direccion: string;


}

