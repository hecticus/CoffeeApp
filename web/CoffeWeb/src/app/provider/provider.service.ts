import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BaseService } from '../common/services/base.service';
import { contentHeaders } from '../common/headers';

import { Provider } from './provider';
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

import { ProviderType } from '../providerType/providerType';
import { ProviderTypeService } from '../providerType/providerType.service';

export interface Filter {
  }

@Injectable()
export class ProviderService extends BaseService
{
	 private urlProvider: string= this.HOST+'/provider';


	constructor(
        private http: Http,
        private providerTypeService: ProviderTypeService
    ){
        super();
        console.log(contentHeaders);
     //   contentHeaders.append("Authorization", sessionStorage.getItem('token'));
    }

    getById(id: number): Observable<Provider> {
        return this.http.get(this.urlProvider + '/' + id, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getAll(requestOptions: RequestOptions = new RequestOptions()): Observable<Provider[]> {
        requestOptions.headers = contentHeaders;
        return this.http.get(this.urlProvider+'', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }

    getAllSearch(requestOptions: RequestOptions = new RequestOptions()): Observable<Provider[]>{
       
       console.log("getAllSearch provider");
        requestOptions.headers = contentHeaders;

        return this.http.get(this.urlProvider + '/search', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);


    }

    new(): Observable<Provider> {
        return this.http.get(this.urlProvider + '/new', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    edit(id: number): Observable<Provider> {
        return this.http.get(this.urlProvider + '/' + id + '', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    create(provider: Provider): Observable<Provider> {
        return this.http.post(this.urlProvider, provider, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);

            
    }

    update(provider: Provider): Observable<Provider> {
        console.log(provider);
        return this.http.put(this.urlProvider, provider, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlProvider + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
        return this.http.post(this.urlProvider + '/deletes', ids, {headers: contentHeaders});
    }

    getQuestions(provider: Provider) {
            let dropdownQuestionProviderType = new DropdownQuestion({
            key: 'id_ProviderType',
            label: 'Tipo del proveedor:',
            value: provider.providerType['idProviderType'],
            optionsKey: 'idProviderType',
            optionsValue: 'nameProviderType',
            required: true,
        });
         
        this.providerTypeService.getAll(this.buildRequestOptionsFinder("name_provider_type", "s")).subscribe(params => { 
                  dropdownQuestionProviderType.options = params['result']; });

        console.log(dropdownQuestionProviderType);
        let questions: Fieldset[] = [
            new Fieldset({
                legend: 'infomación de Proveedor',
                fields: [[
                  new TextboxQuestion({
                        key: 'idProvider',
                        label: 'id',
                        value: provider.idProvider,
                        type: 'number',
                        hidden: true
                    })],[
                    new TextboxQuestion({
                        key: 'nameChange',
                        label: 'nameChange',
                        value: provider.fullNameProvider,
                        type: 'text',
                        hidden: true
                    })],[
                    new TextboxQuestion({
                        key: 'status',
                        label: 'status',
                        value: 0,
                        type: 'number',
                        hidden: true
                    })],
                    [
                    new TextboxQuestion({
                        key: 'identificationDocProvider',
                        label: 'Numero de identificacion del proveedor:',
                        value: provider.identificationDocProvider,
                        type: 'text',
                        required: true
                    })],
                    [
                    new TextboxQuestion({
                        key: 'identificationDocProviderChange',
                        value: provider.identificationDocProvider,
                        type: 'text',
                        hidden: true
                    })],
                    [
                    new TextboxQuestion({
                        key: 'fullNameProvider',
                        label: 'Nombre del proveedor:',
                        value: provider.fullNameProvider,
                        type: 'text',
                        required: true
                    })],[
                    dropdownQuestionProviderType
                ],
                    [
                    new TextboxQuestion({
                        key: 'phoneNumberProvider',
                        label: 'telefono:',
                        value: provider.phoneNumberProvider,
                        type: 'number',
                        required: true
                    })],
                [
                    new TextboxQuestion({
                        key: 'addressProvider',
                        label: 'Direccion:',
                        value: provider.addressProvider,
                        type: 'text',
                        required: true
                    }),
                ],
                [
                    new TextboxQuestion({
                        key: 'emailProvider',
                        label: 'Correo:',
                        value: provider.emailProvider,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxQuestion({
                        key: 'contactNameProvider',
                        label: 'Contacto:',
                        value: provider.contactNameProvider,
                        type: 'text',
                        required: true
                    }),
                ]
                    ]
            })
        ];
        return questions;
    }

    getAnswers(provider: Provider) {
        console.log("******************************");
        console.log(provider);
        console.log("******************************");
        let answers: FieldsetAnswer[] = [
            new FieldsetAnswer({
                legend: 'infomación de Proveedor',
                fields: [[
                    new TextboxAnswer({
                        key: 'fullNameProvider',
                        label: 'Nombre del proveedor:',
                        value: provider.fullNameProvider,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'providerType',
                        label: 'tipo de Proveedor:',
                        value: provider.providerType["nameProviderType"],
                        type: 'number'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'phoneNumberProvider',
                        label: 'telefono:',
                        value: provider.phoneNumberProvider,
                        type: 'number'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'addressProvider',
                        label: 'Direccion:',
                        value: provider.addressProvider,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'emailProvider',
                        label: 'Correo:',
                        value: provider.emailProvider,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'contactNameProvider',
                        label: 'Contacto:',
                        value: provider.contactNameProvider,
                        type: 'text'
                    }),
                ]
                ]
            }),
        ];
        return answers;
    }
}
