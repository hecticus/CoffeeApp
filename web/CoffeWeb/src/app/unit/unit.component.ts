import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { BaseService } from '../common/services/base.service';
import { contentHeaders } from '../common/headers';
import { Unit } from '../common/models/unit';

@Component({
  selector: 'app-unit',
  templateUrl: './unit.component.html',
  styleUrls: ['./unit.component.css']
})
export class UnitComponent extends BaseService implements OnInit {

    private unit: Unit;
    private ver: number;
    private urlUser: string = this.HOST + '/unit';
    private headers: Headers;

  constructor(public router: Router, public http: Http)
  {
    super();
  }

  ngOnInit() {
  //  this.ver=0;
  }


  showUnitGetAll()
  {
      alert("getAll");
  }

closeNav() {
    document.getElementById("unitSidenav").style.width = "0";
    //document.getElementById("home").style.marginLeft= "0";
}

  CallUnitCreate(event, name, idStatus)
  {
    event.preventDefault();
    let body = JSON.stringify({ name, idStatus });
    this.http.post(this.urlUser+'', body, { headers: contentHeaders })
      .subscribe(
        response => {
          if (response.json().result=="null")
          {
            alert(response.json().message);
            
          }
          alert('todo bien');
          this.router.navigate(['home']);
        },
        error => {
          if(error.status===400)
          {
            alert(error.json().message);
          }
          console.log(error.json().message);
        }
      );
  } 

}
