import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';

import { BaseService } from '../../core/base.service';
import { Injectable } from '@angular/core';
import { Lot } from '../../core/models/lot';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class LotService {

  private static readonly BASE_URL: string = BaseService.HOST + '/farm';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder
  ) { }

  create(lot: Lot): Observable<Lot> {
    return this.http.post<Lot>(LotService.BASE_URL, lot);
  }

  update(lot: Lot): Observable<Lot> {
      return this.http.put<Lot>(LotService.BASE_URL + '/' + lot.id, lot);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(LotService.BASE_URL + '/' + id);
  }

  deletes(ids: {'ids': number[]}): Observable<any> {
  return this.http.post(LotService.BASE_URL + '/deletes', ids );
  }

  getById(id: number): Observable<Lot> {
    return this.http.get<Lot>(LotService.BASE_URL + '/' + id);
  }

  getAll(params: HttpParams): Observable<Lot[]> {
    return this.http.get<Lot[]>(LotService.BASE_URL + '/search', {params});
  }

  getAllSearch(params: HttpParams): Observable<Lot[]> {
      return this.http.get<Lot[]>(LotService.BASE_URL + '/search', {params});
  }

  new(): Observable<Lot> {
      return this.http.get<Lot>(LotService.BASE_URL + '/new');
  }
/* 
  getQuestions(lot: Lot) {
        let dropdownQuestionFarm = new DropdownQuestion({
          key: 'farm',
          label: 'Nombre de la Granja:',
          value: lot.farm['idFarm'],
          optionsKey: 'idFarm',
          optionsValue: 'NameFarm',
          required: true,
      });

      this.farmService.getAll(this.buildRequestOptionsFinder("name_farm", "s")).subscribe(params => {
                dropdownQuestionFarm.options = params['result'];
                 console.log(dropdownQuestionFarm.options);
               });

      let dropdownQuestionStatus = new DropdownQuestion({
          key: 'statusLot',
          label: 'Status:',
          value: lot.statusLot,
          optionsKey: 'id',
          optionsValue: 'name',
          required: true,
      });


      dropdownQuestionStatus.options =[{ id: 0, name: "No Activo" }, { id: 1, name: "Activo" }];
        let questions: Fieldset[] = [
          new Fieldset({
              legend: 'infomación de Lote',
              fields: [[
                  new TextboxQuestion({
                      key: 'idLot',
                      label: 'id',
                      value: lot.idLot,
                      type: 'number',
                      hidden: true,
                  })],[
                  new TextboxQuestion({
                      key: 'nameChange',
                      label: 'nameChange',
                      value: lot.nameLot,
                      type: 'text',
                      hidden: true,
                  })],
                  [
                  new TextboxQuestion({
                      key: 'name',
                      label: 'Nombre del Lote:',
                      value: lot.nameLot,
                      type: 'text',
                      required: true,
                  })],[
                  dropdownQuestionFarm
              ],[
                  new TextboxQuestion({
                      key: 'areaLot',
                      label: 'Area:',
                      value: lot.areaLot,
                      type: 'number',
                      required: true,
                  })],[
                  new TextboxQuestion({
                      key: 'heighLot',
                      label: 'Altura:',
                      value: lot.heighLot,
                      type: 'number',
                      required: true,
                  })],[
                  new TextboxQuestion({
                      key: 'price_lot',
                      label: 'US Precio:',
                      value: lot.price_lot,
                      type: 'number',
                      required: true,
                  })
                  ],[
                  dropdownQuestionStatus
              ],]
          })
      ];
      return questions;
  }

  getAnswers(lot: Lot) {
      let answers: FieldsetAnswer[] = [
          new FieldsetAnswer({
              legend: 'infomación de Lote',
              fields: [[
                  new TextboxAnswer({
                      key: 'nameLot',
                      label: 'Nombre del Lote:',
                      value: lot.nameLot,
                      type: 'text'
                  }),
              ],[
                  new TextboxClickAnswer({
                      key: 'nameFarm',
                      label: 'Nombre de la Granja:',
                      value: lot.farm["nameFarm"],
                      type: 'text'
                  }),
              ],
              [
                  new TextboxClickAnswer({
                      key: 'areaLot',
                      label: 'Area:',
                      value: lot.areaLot,
                      type: 'text'
                  }),
              ],
              [
                  new TextboxClickAnswer({
                      key: 'heighLot',
                      label: 'Altura:',
                      value: lot.heighLot,
                      type: 'text'
                  }),
              ],
              [
                  new TextboxClickAnswer({
                      key: 'price_lot',
                      label: 'US Precio:',
                      value: lot.price_lot,
                      type: 'text'
                  }),
              ],
              [
                  new TextboxClickAnswer({
                      key: 'statusLot',
                      label: 'status',
                      value: (lot.statusLot == "0") ? " No Activo":"Activo",
                      type: 'text'
                  }),
              ],
              ]
          }),
      ];
      return answers;
  } */
}


