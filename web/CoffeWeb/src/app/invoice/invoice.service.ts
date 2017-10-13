import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { BaseService } from '../common/services/base.service';
import { contentHeaders } from '../common/headers';

import { Invoice } from './invoice';
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
  }

@Injectable()
export class InvoiceService extends BaseService
{
	 private urlInvoice: string= this.HOST+'/invoice';


	constructor(
        private http: Http,
    ){
        super();
      //  console.log(contentHeaders);
        contentHeaders.delete("Authorization");
        contentHeaders.append("Authorization", sessionStorage.getItem('token'));
    }

    getById(id: number): Observable<Invoice> {
        return this.http.get(this.urlInvoice + '/' + id, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getAll(requestOptions: RequestOptions = new RequestOptions()): Observable<Invoice[]> {
        requestOptions.headers = contentHeaders;
        return this.http.get(this.urlInvoice+'', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);
    }

    getAllSearch(requestOptions: RequestOptions = new RequestOptions()): Observable<Invoice[]>{
       
       console.log("getAllSearch invoice");
        requestOptions.headers = contentHeaders;

        return this.http.get(this.urlInvoice + '/search', requestOptions)
            .map(this.extractDataFull)
            .catch(this.handleError);


    }

    new(): Observable<Invoice> {
        return this.http.get(this.urlInvoice + '/new', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    edit(id: number): Observable<Invoice> {
        return this.http.get(this.urlInvoice + '/' + id + '', {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    create(invoice: Invoice): Observable<Invoice> {
        return this.http.post(this.urlInvoice, invoice, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);

            
    }

    update(invoice: Invoice): Observable<Invoice> {
        return this.http.put(this.urlInvoice, invoice, {headers: contentHeaders})
            .map(this.extractData)
            .catch(this.handleError);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(this.urlInvoice + '/' + id, {headers: contentHeaders});
    }

    deletes(ids: {"ids": number[]}): Observable<any> {
        return this.http.post(this.urlInvoice + '/deletes', ids, {headers: contentHeaders});
    }

    getQuestions(invoice: Invoice) {
   /*         let dropdownQuestionInvoiceType = new DropdownQuestion({
            key: 'id_InvoiceType',
            label: 'Tipo del proveedor:',
            value: invoice.invoiceType['idInvoiceType'],
            optionsKey: 'idInvoiceType',
            optionsValue: 'nameInvoiceType',
            required: true,
        });
         
        this.invoiceTypeService.getAll(this.buildRequestOptionsFinder("name_invoice_type", "s")).subscribe(params => { 
                  dropdownQuestionInvoiceType.options = params['result']; });
*/
        let questions: Fieldset[] = [
            new Fieldset({
                legend: 'infomación de Proveedor',
                fields: [[
                  new TextboxQuestion({
                        key: 'idInvoice',
                        label: 'id',
                        value: invoice.idInvoice,
                        type: 'number',
                        hidden: true
                    })],/*[
                    new TextboxQuestion({
                        key: 'nameChange',
                        label: 'nameChange',fullNameInvoice
                        value: invoice.fullNameInvoice,
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
                        key: 'identificationDocInvoice',
                        label: 'Numero de identificacion del proveedor:',
                        value: invoice.identificationDocInvoice,
                        type: 'text',
                        required: true
                    })],
                    [
                    new TextboxQuestion({
                        key: 'identificationDocInvoiceChange',
                        value: invoice.identificationDocInvoice,
                        type: 'text',
                        hidden: true
                    })],
                    [
                    new TextboxQuestion({
                        key: 'fullNameInvoice',
                        label: 'Nombre del proveedor:',
                        value: invoice.fullNameInvoice,
                        type: 'text',
                        required: true
                    })],[
                    dropdownQuestionInvoiceType
                ],
                    [
                    new TextboxQuestion({
                        key: 'phoneNumberInvoice',
                        label: 'telefono:',
                        value: invoice.phoneNumberInvoice,
                        type: 'number',
                        required: true
                    })],
                [
                    new TextboxQuestion({
                        key: 'addressInvoice',
                        label: 'Direccion:',
                        value: invoice.addressInvoice,
                        type: 'text',
                        required: true
                    }),
                ],
                [
                    new TextboxQuestion({
                        key: 'emailInvoice',
                        label: 'Correo:',
                        value: invoice.emailInvoice,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxQuestion({
                        key: 'contactNameInvoice',
                        label: 'Contacto:',
                        value: invoice.contactNameInvoice,
                        type: 'text',
                        required: true
                    }),
                ]*/
                    ]
            })
        ];
        return questions;
    }

    getAnswers(invoice: Invoice) {

        let answers: FieldsetAnswer[] = [
            new FieldsetAnswer({
                legend: 'infomación de Proveedor',
                fields: [ /* [
                    new TextboxAnswer({
                        key: 'fullNameInvoice',
                        label: 'Nombre del proveedor:',
                        value: invoice.fullNameInvoice,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'invoiceType',
                        label: 'tipo de Proveedor:',
                        value: invoice.invoiceType["nameInvoiceType"],
                        type: 'number'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'phoneNumberInvoice',
                        label: 'telefono:',
                        value: invoice.phoneNumberInvoice,
                        type: 'number'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'addressInvoice',
                        label: 'Direccion:',
                        value: invoice.addressInvoice,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'emailInvoice',
                        label: 'Correo:',
                        value: invoice.emailInvoice,
                        type: 'text'
                    }),
                ],
                [
                    new TextboxAnswer({
                        key: 'contactNameInvoice',
                        label: 'Contacto:',
                        value: invoice.contactNameInvoice,
                        type: 'text'
                    }),
                ]*/
                ]
            }),
        ];
        return answers;
    }
}
