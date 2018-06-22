import { Component, OnInit } from '@angular/core';
import { BaseService } from '../../core/base.service';

@Component({
  selector: 'app-lot',
  templateUrl: './lot.component.html',
  styleUrls: ['./lot.component.css']
})
export class LotComponent implements OnInit {

  private static readonly BASE_URL: string = BaseService.HOST + '/farm';

  constructor() { }

  ngOnInit() {
  }

}
