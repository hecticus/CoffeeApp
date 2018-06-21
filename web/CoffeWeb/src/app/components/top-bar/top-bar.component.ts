import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  user: String = 'hola';

  constructor() { }

  ngOnInit() {
  }

  public logout() {	}
  public password() {	}
}
