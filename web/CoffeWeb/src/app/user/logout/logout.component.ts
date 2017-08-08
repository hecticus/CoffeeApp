import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';
import { AuthHttp } from 'angular2-jwt';
import { Injectable } from '@angular/core';


@Component({
  selector: 'app-logout',
  template: '<router-outlet></router-outlet>',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
@Injectable()
export class LogoutComponent implements OnInit {

  constructor(public router: Router, public http: Http, public authHttp: AuthHttp) { }

  ngOnInit() {
        localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

}
