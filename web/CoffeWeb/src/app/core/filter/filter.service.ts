import { Injectable } from '@angular/core';

@Injectable()
export class FilterService {
	filter = {};

	put(key: string, value: any) {
		if (value && value !== 'undefined') {
			this.filter[key] = value;
		} else {
			delete this.filter[key];
		}
	}

	reset() {
		this.filter = {};
	}
}
