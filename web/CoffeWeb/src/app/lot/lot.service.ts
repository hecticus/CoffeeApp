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

export interface Filter {
    farmId?: number;
}

@Injectable()
export class LotService extends BaseService
{
	 private urlLot: string= this.HOST+'/lot';


	constructor(
        private http: Http
    ){
        super();
        contentHeaders.append("Authorization", localStorage.getItem('token'));
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
        return this.http.post(this.urlLot, Lot, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    update(lot: Lot): Observable<Lot> {
        return this.http.put(this.urlLot + '/' + lot.idLot, lot, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlLot + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
        return this.http.post(this.urlLot + '/delete', ids, {headers: contentHeaders});
    }

    getQuestions(lot: Lot) {
        let dropdownQuestionFarm = new DropdownQuestion({
            key: 'farm.id',
            label: 'farm.name',
            value: lot.farm != undefined? lot.farm.id: '',
            optionsKey: 'nameLot',
            required: true,
        });
      /*  this.farmService.getAll(this.buildRequestOptionsFinder("name", "s")).subscribe(params => { 
            dropdownQuestionFarm.options = params['result'];
        });*/

          let questions: Fieldset[] = [
            new Fieldset({
                legend: 'infomación de Lote',
                fields: [[
                    new TextboxQuestion({
                        key: 'idLot',
                        label: 'idLot',
                        value: lot.idLot,
                        type: 'number',
                        hidden: true,
                    }),
                    dropdownQuestionFarm
                ]]
            })
        ];
        return questions;
    }

    getAnswers(lot: Lot) {
        console.log(lot);
        let answers: FieldsetAnswer[] = [
            new FieldsetAnswer({
                legend: 'infomación de Lote',
                fields: [[
                    new TextboxAnswer({
                        key: 'nameLot',
                        label: 'name',
                        value: lot.nameLot,
                        type: 'text'
                    }),
                ],[
                    new TextboxClickAnswer({
                        key: 'price',
                        label: 'price',
                        value: lot.price_lot,
                        type: 'text'
                    }),
                ]]
            }),
        ];
        return answers;
    }
}
