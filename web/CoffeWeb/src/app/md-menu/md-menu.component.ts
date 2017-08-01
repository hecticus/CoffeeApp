import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-md-menu',
  templateUrl: './md-menu.component.html',
  styleUrls: ['./md-menu.component.css']
})
export class MdMenuComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }


 openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    //document.getElementById("home").style.marginLeft = "250px";
}

closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    //document.getElementById("home").style.marginLeft= "0";
}

}
