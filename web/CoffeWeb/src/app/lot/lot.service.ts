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
    serial?: string;
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
        return this.http.get(this.urlLot, requestOptions)
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
        return this.http.get(this.urlLot + '/' + id + '/edit', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    create(Lot: Lot): Observable<Lot> {
        return this.http.post(this.urlLot, Lot, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    update(Lot: Lot): Observable<Lot> {
        return this.http.put(this.urlLot + '/' + Lot.id, Lot, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlLot + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
        return this.http.post(this.urlLot + '/delete', ids, {headers: contentHeaders});
    }

    getQuestions(Lot: Lot) {
      /*  let dropdownQuestionStore = new DropdownQuestion({
            key: 'store.id',
            label: 'store.name',
            value: Lot.store != undefined? Lot.store.id: '',
            optionsKey: 'name',
            required: true,
        });
        this.storeService.getAll(this.buildRequestOptionsFinder("name", "s")).subscribe(params => { 
            dropdownQuestionStore.options = params['result'];
        });

        let dropdownQuestionLotModel = new DropdownQuestion({
            key: 'statusLot.id',
            label: 'statusLot',
            value: Lot.statusLot != undefined? Lot.statusLot.id: '',
            optionsKey: 'name'
        });
        this.LotModelService.getAll(this.buildRequestOptionsFinder("name")).subscribe(params => {
            dropdownQuestionLotModel.options = params['result'];
        });

        let dropdownQuestionStatusLot = new DropdownQuestion({
            key: 'statusLot.id',
            label: 'statusLot',
            value: Lot.statusLot != undefined? Lot.statusLot.id: '',
            optionsKey: 'name'
        });
        this.statusLotService.getAll(this.buildRequestOptionsFinder("name")).subscribe(params => {
            dropdownQuestionStatusLot.options = params['result'];
        });

        let questions: Fieldset[] = [
            new Fieldset({
                legend: 'infomación de máquina',
                fields: [[
                    new TextboxQuestion({
                        key: 'id',
                        label: 'id',
                        value: Lot.id,
                        type: 'number',
                        hidden: true,
                    }),
                    dropdownQuestionStore
                ],[
                    new TextboxQuestion({
                        key: 'serial',
                        label: 'serial',
                        value: Lot.serial,
                        type: 'text',
                        required: true,
                    }),
                ],[
                   dropdownQuestionLotModel
                ],[
                    dropdownQuestionStatusLot
                ],[
                    new DatePickerQuestion({
                        key: 'purchaseDate',
                        label: 'purchaseDate',
                        value: Lot.purchaseDate
                    }),
                    new DatePickerQuestion({
                        key: 'factoryWarrantyExpiration',
                        label: 'factoryWarrantyExpiration',
                        value: Lot.factoryWarrantyExpiration
                    }),
                    new NumberboxQuestion({
                        key: 'localWarrantyDay',
                        label: 'localWarrantyDay',
                        value: Lot.localWarrantyTime,
                    }),
                ]]
            }),
            new Fieldset({
                legend: 'infomación de uso y mantenimiento',
                fields: [[
                    new TextboxQuestion({
                        key: 'swVersion',
                        label: 'swVersion',
                        value: Lot.swVersion,
                        type: 'text',
                    }),
                ],[
                    new NumberboxQuestion({
                        key: 'usedTime',
                        label: 'usedTime',
                        value: Lot.usedTime,
                        step: 0.0000001,
                    }),
                    new NumberboxQuestion({
                        key: 'usedDistance',
                        label: 'usedDistance',
                        value: Lot.usedDistance,
                        step: 0.0000001,
                    }),
                ],[
                    new NumberboxQuestion({
                        key: 'maintenenceFrecuency',
                        label: 'maintenenceFrecuency',
                        value: Lot.maintenenceFrecuency,
                    }),
                    new NumberboxQuestion({
                        key: 'maintenencePerDistance',
                        label: 'maintenencePerDistance',
                        value: Lot.maintenencePerDistance,
                    }),
                    new NumberboxQuestion({
                        key: 'maintenencePerTime',
                        label: 'maintenencePerTime',
                        value: Lot.maintenencePerTime,
                    }),
                ]]
            })
        ];*/
        let questions: Fieldset[];
        return questions;
    }

    getAnswers(Lot: Lot) {
       /* let answers: FieldsetAnswer[] = [
            new FieldsetAnswer({
                legend: 'infomación de máquina',
                fields: [[
                    new TextboxClickAnswer({
                        key: 'store.name',
                        label: 'store.name',
                        value: Lot.store.name,
                        type: 'text',
                        answers: storeAnswers
                    }),
                ],[
                    new TextboxAnswer({
                        key: 'serial',
                        label: 'serial',
                        value: Lot.serial,
                        type: 'text'
                    }),
                ],[
                    new TextboxClickAnswer({
                        key: 'LotModel.comercialName',
                        label: 'LotModel.comercialName',
                        value: Lot.LotModel.comercialName,
                        type: 'text',
                        answers: LotModelAnswers
                    }),
                ],[
                    new TextboxAnswer({
                        key: 'statusLot.name',
                        label: 'statusLot',
                        value: Lot.statusLot != undefined ? Lot.statusLot.name : '',
                        type: 'text'
                    })
                ],[
                    new DatePickerAnswer({
                        key: 'purchaseDate',
                        label: 'purchaseDate',
                        value: Lot.purchaseDate,
                    }),
                ],[
                    new TextboxAnswer({
                        key: 'localWarrantyTime',
                        label: 'localWarrantyTime',
                        value: Lot.localWarrantyTime,
                        type: 'number'
                    }),
                    new DatePickerAnswer({
                        key: 'factoryWarrantyExpiration',
                        label: 'factoryWarrantyExpiration',
                        value: Lot.factoryWarrantyExpiration,
                    }),
                ]]
            }),
            new FieldsetAnswer({
                legend: 'infomación de uso y mantenimiento',
                fields: [[
                    new TextboxAnswer({
                        key: 'swVersion',
                        label: 'swVersion',
                        value: Lot.swVersion,
                        type: 'text'
                    }),
                ],[
                    new TextboxAnswer({
                        key: 'usedDistance',
                        label: 'usedDistance',
                        value: Lot.usedDistance,
                        type: 'number'
                    }),
                    new TextboxAnswer({
                        key: 'usedTime',
                        label: 'usedTime',
                        value: Lot.usedTime,
                        type: 'number'
                    }),
                ],[
                    new TextboxAnswer({
                        key: 'maintenenceFrecuency',
                        label: 'maintenenceFrecuency',
                        value: Lot.maintenenceFrecuency,
                        type: 'number'
                    }),
                    new TextboxAnswer({
                        key: 'maintenencePerDistance',
                        label: 'maintenencePerDistance',
                        value: Lot.maintenencePerDistance,
                        type: 'number'
                    }),
                    new TextboxAnswer({
                        key: 'maintenencePerTime',
                        label: 'maintenencePerTime',
                        value: Lot.maintenencePerTime,
                        type: 'number'
                    }),
                ]]
            })
        ];*/
        let answers: FieldsetAnswer[];
        return answers;
    }
}
