import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BaseService } from '../common/services/base.service';
import { contentHeaders } from '../common/headers';

import { Farm } from './farm';
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

export interface Filter {
    farmId?: number;
}

@Injectable()
export class FarmService extends BaseService
{
	 private urlFarm: string= this.HOST+'/farm';


	constructor(
        private http: Http
    ){
        super();
     //  contentHeaders.append("Authorization", localStorage.getItem('token'));
    }

    getById(id: number): Observable<Farm> {
        return this.http.get(this.urlFarm + '/' + id, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getAll(requestOptions: RequestOptions = new RequestOptions()): Observable<Farm[]> {
        requestOptions.headers = contentHeaders;
        return this.http.get(this.urlFarm+'', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }

    getAllSearch(requestOptions: RequestOptions = new RequestOptions()): Observable<Farm[]>{
        requestOptions.headers = contentHeaders;
        //console.log(requestOptions);
        return this.http.get(this.urlFarm + '/search', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }
/*
    new(): Observable<Farm> {
        return this.http.get(this.urlFarm + '/new', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    edit(id: number): Observable<Farm> {
        return this.http.get(this.urlFarm + '/' + id + '', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    create(Farm: Farm): Observable<Farm> {
        return this.http.post(this.urlFarm, Farm, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);

            
    }

    update(Farm: Farm): Observable<Farm> {
        
        return this.http.put(this.urlFarm /*+ '/' + Farm.id* /, Farm, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlFarm + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
        return this.http.post(this.urlFarm + '/delete', ids, {headers: contentHeaders});
    }

  /*  getQuestions(Farm: Farm) {
    

          let questions: Fieldset[] = [
            new Fieldset({
                legend: 'infomación de Lote',
                fields: [[
                    new TextboxQuestion({
                        key: 'idLot',
                        label: 'id',
                        value: Farm.idLot,
                        type: 'number',
                        hidden: true,
                    })],[
                    new TextboxQuestion({
                        key: 'farm',
                        label: 'id_farm',
                        value: 1,
                        type: 'number',
                        hidden: true,
                    })],[
                    new TextboxQuestion({
                        key: 'status',
                        label: 'status',
                        value: 0,
                        type: 'number',
                        hidden: true,
                    })],
                    [
                    new TextboxQuestion({
                        key: 'name',
                        label: 'Nombre del Lote:',
                        value: Farm.nameLot,
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'nameFarm',
                        label: 'Nombre de la Finca:',
                        value: Farm.farm["nameFarm"],
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'areaLot',
                        label: 'Area:',
                        value: Farm.areaLot,
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'heighLot',
                        label: 'Altura:',
                        value: Farm.heighLot,
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'price_lot',
                        label: 'US Precio:',
                        value: Farm.price_lot,
                        type: 'text',
                        required: true,
                    })
                ]]
            })
        ];
        return questions;
    }

    getAnswers(Farm: Farm) {
          let answers: FieldsetAnswer[] = [
            new FieldsetAnswer({
                legend: 'infomación de Lote',
                fields: [[
                    new TextboxAnswer({
                        key: 'nameLot',
                        label: 'Nombre del Lote:',
                        value: Farm.nameLot,
                        type: 'text'
                    }),
                ],[
                    new TextboxClickAnswer({
                        key: 'nameFarm',
                        label: 'Nombre de la Finca:',
                        value: Farm.farm["nameFarm"],
                        type: 'text'
                    }),
                ],
                [
                    new TextboxClickAnswer({
                        key: 'areaLot',
                        label: 'Area:',
                        value: Farm.areaLot,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxClickAnswer({
                        key: 'heighLot',
                        label: 'Altura:',
                        value: Farm.heighLot,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxClickAnswer({
                        key: 'price_lot',
                        label: 'US Precio:',
                        value: Farm.price_lot,
                        type: 'text'
                    }),
                ]
                ]
            }),
        ];
        return answers;
    }*/
}
