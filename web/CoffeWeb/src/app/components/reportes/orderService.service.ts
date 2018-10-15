import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { BaseService } from 'app/core/base.service';
import { OrderService } from './orderService';
import { CustomValidators } from 'app/shared/form/custom.validators';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';

@Injectable()
export class OrderServiceService {
	private static readonly BASE_URL: string = BaseService.HOST + '/orderServices';

	constructor(
		private http: HttpClient,
		private fb: FormBuilder
	) {}

	getById(id: number): Observable<OrderService> {
		return this.http.get(OrderServiceService.BASE_URL + '/' + id)
			.map(BaseService.extractData)
			.catch(BaseService.handleError);
	}

	getAll(params: HttpParams): Observable<any> {
		return this.http.get(OrderServiceService.BASE_URL, {params: params})
			.catch(BaseService.handleError);
	}

	create(orderService: OrderService): Observable<OrderService> {
		return this.http.post(OrderServiceService.BASE_URL, orderService)
			.map(BaseService.extractData)
			.catch(BaseService.handleError);
	}

	update(orderService: OrderService): Observable<OrderService> {
		return this.http.put(OrderServiceService.BASE_URL + '/' + orderService.id, orderService)
			.map(BaseService.extractData)
			.catch(BaseService.handleError);
	}

	delete(id: number): Observable<any> {
		return this.http.delete(OrderServiceService.BASE_URL + '/' + id)
			.catch(BaseService.handleError);
	}

	deletes(orderServices: OrderService[]): Observable<any> {
		return this.http.post(OrderServiceService.BASE_URL + '/delete', orderServices)
			.catch(BaseService.handleError);
	}

	toFormGroup(orderService: OrderService): FormGroup {
		let machineModels;
		if (orderService.machineModels) {
			machineModels = orderService.machineModels.map(machineModel => { return {id: machineModel.id}; });
		}

		return this.fb.group({
			id: new FormControl(orderService.id),
			name: new FormControl(orderService.name, [Validators.required, Validators.maxLength(100)]),
			workedTime: new FormControl(orderService.workedTime, [CustomValidators.integerRegex, CustomValidators.min(0)]),
			price: new FormControl(orderService.price, [CustomValidators.numberRegex, CustomValidators.min(0)]),
			description: new FormControl(orderService.description),
			machineModels: new FormControl(machineModels, Validators.required)
		});
	}
}
