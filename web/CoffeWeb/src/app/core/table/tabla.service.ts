import { Injectable } from '@angular/core';
import { Pager } from '../models/pager';

@Injectable({
	providedIn: 'root'
})
export class TablaService {

	selecteds: {} = {};	// use for items selected
	pager: Pager = {pageIndex: 0, pageSize: 15};
	sort: string;
	valueKey = 'id';

	select(item) {
		this.selecteds = {};
		this.selecteds[item[this.valueKey]] = item;
	}

	selects(items: any[]) {
		this.selecteds = {};
		items.forEach(item => this.selecteds[item[this.valueKey]] = item);
	}

	selectToggle(item) {
		if (item[this.valueKey] in this.selecteds) {
			delete this.selecteds[item[this.valueKey]];
		} else {
			this.selecteds[item[this.valueKey]] = item;
		}
	}

	selectAllToggle($event, items: any[]) {
		if ($event.target.checked) {
			items.forEach(item => this.selecteds[item[this.valueKey]] = item);
		} else {
			this.selecteds = {};
		}
	}

	deselect(key: any) {
		if (key in this.selecteds) {
			delete this.selecteds[key];
		}
	}

	deselects(items?: any[]) {
		if (items) {
			items.forEach(item => this.selecteds[item[this.valueKey]] = item);
		} else {
			this.selecteds = {};
		}
	}

	getSelectedsKey(): any[] {
		return Object.keys(this.selecteds);
	}

	getSelectedsValue(): any[] {
		const objects: any[] = [];
		Object.keys(this.selecteds).forEach(key => objects.push(this.selecteds[key]));
		return objects;
	}

	getSelectedsValueKey(): any[] {
		let objects: any[] = [];
		Object.keys(this.selecteds).forEach(key => {
			let obj = {};
			obj[this.valueKey] = key;
			objects.push(obj);
		});
		return objects;
	}

	getSelectedsLength(): number {
		return Object.keys(this.selecteds).length;
	}

	getItemValue(item, columnKey) {
		let keySplits = columnKey.split('.');
		let innerObj = item;

		for (let i = 0; i < keySplits.length - 1; ++i) {
			if (!innerObj[keySplits[i]]) {
				return '';
			}
			innerObj = innerObj[keySplits[i]];
		}

		return innerObj[keySplits[keySplits.length - 1]];
	}
}
