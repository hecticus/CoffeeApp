import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BaseService } from '../common/services/base.service';
import { contentHeaders } from '../common/headers';

import { ProviderType } from './providerType';
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
    ProviderTypeId?: number;
}

@Injectable()
export class ProviderTypeService extends BaseService
{
	 private urlProviderType: string= this.HOST+'/providerType';


	constructor(
        private http: Http
    ){
        super();
     //  contentHeaders.append("Authorization", localStorage.getItem('token'));
    }

    getById(id: number): Observable<ProviderType> {
        return this.http.get(this.urlProviderType + '/' + id, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getAll(requestOptions: RequestOptions = new RequestOptions()): Observable<ProviderType[]> {
      console.log("getAll providerType");
       requestOptions.headers = contentHeaders;
      console.log(requestOptions);
        return this.http.get(this.urlProviderType+'/findAll/0/100', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }

    getAllSearch(requestOptions: RequestOptions = new RequestOptions()): Observable<ProviderType[]>{
        requestOptions.headers = contentHeaders;
        //console.log(requestOptions);
        return this.http.get(this.urlProviderType + '/search', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }
/*
    new(): Observable<ProviderType> {
        return this.http.get(this.urlProviderType + '/new', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    edit(id: number): Observable<ProviderType> {
        return this.http.get(this.urlProviderType + '/' + id + '', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    create(ProviderType: ProviderType): Observable<ProviderType> {
        return this.http.post(this.urlProviderType, ProviderType, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);

            
    }

    update(ProviderType: ProviderType): Observable<ProviderType> {
        
        return this.http.put(this.urlProviderType /*+ '/' + ProviderType.id* /, ProviderType, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlProviderType + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
        return this.http.post(this.urlProviderType + '/delete', ids, {headers: contentHeaders});
    }

  /*  getQuestions(ProviderType: ProviderType) {
    

          let questions: Fieldset[] = [
            new Fieldset({
                legend: 'infomación de Lote',
                fields: [[
                    new TextboxQuestion({
                        key: 'idLot',
                        label: 'id',
                        value: ProviderType.idLot,
                        type: 'number',
                        hidden: true,
                    })],[
                    new TextboxQuestion({
                        key: 'ProviderType',
                        label: 'id_ProviderType',
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
                        value: ProviderType.nameLot,
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'nameProviderType',
                        label: 'Nombre de la Finca:',
                        value: ProviderType.ProviderType["nameProviderType"],
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'areaLot',
                        label: 'Area:',
                        value: ProviderType.areaLot,
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'heighLot',
                        label: 'Altura:',
                        value: ProviderType.heighLot,
                        type: 'text',
                        required: true,
                    })],[
                    new TextboxQuestion({
                        key: 'price_lot',
                        label: 'US Precio:',
                        value: ProviderType.price_lot,
                        type: 'text',
                        required: true,
                    })
                ]]
            })
        ];
        return questions;
    }

    getAnswers(ProviderType: ProviderType) {
          let answers: FieldsetAnswer[] = [
            new FieldsetAnswer({
                legend: 'infomación de Lote',
                fields: [[
                    new TextboxAnswer({
                        key: 'nameLot',
                        label: 'Nombre del Lote:',
                        value: ProviderType.nameLot,
                        type: 'text'
                    }),
                ],[
                    new TextboxClickAnswer({
                        key: 'nameProviderType',
                        label: 'Nombre de la Finca:',
                        value: ProviderType.ProviderType["nameProviderType"],
                        type: 'text'
                    }),
                ],
                [
                    new TextboxClickAnswer({
                        key: 'areaLot',
                        label: 'Area:',
                        value: ProviderType.areaLot,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxClickAnswer({
                        key: 'heighLot',
                        label: 'Altura:',
                        value: ProviderType.heighLot,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxClickAnswer({
                        key: 'price_lot',
                        label: 'US Precio:',
                        value: ProviderType.price_lot,
                        type: 'text'
                    }),
                ]
                ]
            }),
        ];
        return answers;
    }*/
}
