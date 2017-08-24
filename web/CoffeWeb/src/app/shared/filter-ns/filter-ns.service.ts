import { Injectable } from '@angular/core';

@Injectable()
export class FilterService {
	filter = {};
	
	changeFilter(key: string, value: any){
		switch(typeof value) { 
            case "number": {
                if(value != -1)
					this.filter[key] = value;
				else
					delete this.filter[key];
                break; 
            } 
            case "string": { 
                if(value != '' && value != '-1')
					this.filter[key] = value;
				else
					delete this.filter[key];
                break; 
            } 
        }
	}
}