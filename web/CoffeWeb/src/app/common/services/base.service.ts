import { Router } from '@angular/router';
import { contentHeaders } from '../headers';
import { Http, Headers, Response, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';


export class BaseService{ 

   public HOST: string = 'https://dev.api.coffee.hecticus.com';
//  public HOST: string = 'http://localhost:9000';
    public PAGE_SIZE: string = "4";


    public extractData(res: Response) {
        let body = res.json();
        //console.log(body);
        return body.result || { };
    }

    public extractDataFull(res: Response) {
        return res.json();
    }

    public extractHeaders(res: Response) {
        return res.headers;
    }

    public extractDataAndHeaders(res: Response) { 
        let body = res.json();
        return { data: body.result || { }, headers: res.headers };
    }

    public handleResponse(res: Response) {
        return res.ok;
    }

    public handleError (error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            // errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
            errMsg = JSON.parse(JSON.stringify({status: error.status, body: body}));
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        //console.error(errMsg);
        return Observable.throw(errMsg);
    }

    //http://stackoverflow.com/questions/18936915/dynamically-set-property-of-nested-object
    public builderObject(json: {}){
        let obj = {};

        for(var key in json){
            let innerObj = obj;
            let keySplits = key.split('.');

            for(var i = 0; i < keySplits.length-1; ++i){
                let keySplit = keySplits[i];  
                if( !innerObj[keySplit] ) innerObj[keySplit] = {};
                    innerObj = innerObj[keySplit];
            }

            innerObj[keySplits[keySplits.length-1]] = json[key];
        }
        return obj;
    }

    public removeItem(items: any[], id: number){
        for(let i = 0; items.length; ++i)
            if(items[i].id == id){
                items.splice(i, 1);
                return;
            }
    }

    public removeItems(items: any[], ids: number[]){
        ids.map(id => this.removeItem(items, id));
    }

      public createAuthorizationHeader(): Headers {
         contentHeaders.append("Authorization", localStorage.getItem('token'));
        return contentHeaders;
    }

     public buildRequestOptionsFinder(sort?: string, collection?: string, all?:string, filter?: {}, pager?: {pageIndex: number, pageSize: number}): RequestOptions{
        let params: URLSearchParams = new URLSearchParams();

        if(sort != undefined){
            params.set('sort', sort);
        }
        if(collection != undefined){
            params.set('collection', collection);
        }

        if(all!=undefined)
        {
           params.set('all', all); 
        }
        for (var key in filter){
            params.set(key, filter[key].toString());
        }
        if(pager != undefined){
            params.set('pager.index', pager.pageIndex.toString());
            params.set('pager.size', pager.pageSize.toString());
        }

        let requestOptions = new RequestOptions();
        requestOptions.search = params;

        return requestOptions;
    }
}