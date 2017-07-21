import { Component, OnInit } from '@angular/core';
import { Farm} from '../../common/models/farm';
import { BaseService } from '../../common/services/base.service';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { contentHeaders } from '../../common/headers';
@Component({
  selector: 'app-farm-services',
  templateUrl: './farm-services.component.html',
  styleUrls: ['./farm-services.component.css']
})
export class FarmServicesComponent extends BaseService implements OnInit  {
private farm: Farm;
      private urlUser: string = this.HOST + '/farm';
      private headers: Headers;
  constructor(public router: Router, public http: Http)
  {
     super();
     contentHeaders.append('Authorization',localStorage.getItem('token'));
  }

  ngOnInit() {
  }
  
farmCreate(event, name, idStatus)
{
event.preventDefault();
    let body = JSON.stringify({ name, idStatus });
      this.http.post(this.urlUser+'/create', body, { headers: contentHeaders })
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
