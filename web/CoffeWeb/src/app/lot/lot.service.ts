import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BaseService } from '../common/services/base.service';
import { contentHeaders } from '../common/headers';

import { Lot } from './lot';
import { Fieldset } from '../shared/dynamic-form/question/fieldset';
import { TextboxQuestion } from '../shared/dynamic-form/question/question-textbox';
import { NumberboxQuestion } from '../shared/dynamic-form/question/question-numberbox';
import { TextareaQuestion } from '../shared/dynamic-form/question/question-textarea';
import { DropdownQuestion } from '../shared/dynamic-form/question/question-dropdown';
import { DatePickerQuestion } from '../shared/dynamic-form/question/question-datepicker';
import { FieldsetAnswer } from '../shared/dynamic-show/answer/fieldset';
import { AnswerBase } from '../shared/dynamic-show/answer/answer-base';
import { TextboxAnswer } from '../shared/dynamic-show/answer/answer-textbox';
import { TextareaAnswer } from '../shared/dynamic-show/answer/answer-textarea';
import { TextboxClickAnswer } from '../shared/dynamic-show/answer/answer-textbox-click';
import { DropdownAnswer } from '../shared/dynamic-show/answer/answer-dropdown';
import { DatePickerAnswer } from '../shared/dynamic-show/answer/answer-datepicker';

import { Farm } from '../farm/farm';
import { FarmService } from '../farm/farm.service';

export interface Filter {
    farmId?: number;
}

@Injectable()
export class LotService extends BaseService
{
	 private urlLot: string= this.HOST+'/lot';


	constructor(
        private http: Http,
        private farmService: FarmService
    ){
        super();
     //   console.log(contentHeaders);
     //   contentHeaders.append("Authorization", sessionStorage.getItem('token'));
    }

    getById(id: number): Observable<Lot> {
        return this.http.get(this.urlLot + '/' + id, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getAll(requestOptions: RequestOptions = new RequestOptions()): Observable<Lot[]> {
        requestOptions.headers = contentHeaders;
        return this.http.get(this.urlLot+'', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }

    getAllSearch(requestOptions: RequestOptions = new RequestOptions()): Observable<Lot[]>{
       requestOptions.headers = contentHeaders;
        console.log("aq2ui");
        return this.http.get(this.urlLot + '/search', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);

    }

    new(): Observable<Lot> {
        return this.http.get(this.urlLot + '/new', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    edit(id: number): Observable<Lot> {
        return this.http.get(this.urlLot + '/' + id + '', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    create(lot: Lot): Observable<Lot> {
        console.log(lot);
        return this.http.post(this.urlLot, lot, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);

            
    }

    update(lot: Lot): Observable<Lot> {
         console.log(lot);
        return this.http.put(this.urlLot, lot, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlLot + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
       let f = ids.ids;
       console.log(f);
        return this.http.post(this.urlLot + '/deletes', ids, {headers: contentHeaders});
      /* for(let id of f)
	   {
           console.log(id);
         let res = this.http.delete(this.urlLot + '/' + id, {headers: contentHeaders});
       }
    return this.http.delete(this.urlLot + '/' + 0, {headers: contentHeaders});*/
    }

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
    }
}
