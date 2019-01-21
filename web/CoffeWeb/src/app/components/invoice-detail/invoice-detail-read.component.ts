import { invoiceDetailRoutes } from './invoice-detail..routing';
import { InvoiceDetail } from './../../core/models/invoice-detail';
import { InvoiceDetailService } from './invoice-detail.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { NotificationService } from 'src/app/core/utils/notification/notification.service';

@Component({
	styleUrls: ['./invoice-detail.component.css'],
	template: `
		<h3 class="title">Detalle del Item</h3>
		<!---->
		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" title="Actualizar item" type="button" (click)="update()">
					<i class="material-icons">edit</i>
				</button>
				<!--
				<button class="btn-icon" title="Eliminar Item de la Factura" type="button" (click)="openModal(template)">
					<i class="material-icons">delete</i>
				</button>-->
			</div>
		</div>

		<div class="answer">
			<div class="fieldset">
				<div class="wrap-fields">
					<div>
						<span class="label">Fecha de Apertura</span>
						<span class="output">{{ invoiceDetail.startDate || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
						<span class="label">Tipo de Item</span>
						<span class="output">{{ invoiceDetail.itemType?.nameItemType || '-'}}</span>
				</div>
				<div *ngIf="option == 1">
				<div class="wrap-fields">
					<div>
						<span class="label">Recibido</span>
						<span class="output">{{ invoiceDetail.nameReceived || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label">Entregado</span>
						<span class="output">{{invoiceDetail.nameDelivered || '-'}}</span>
					</div>
				</div>
				<div class="wrap-fields">
					<div>
						<span class="label" >Nombre de la Tienda</span>
						<span class="output">{{ invoiceDetail.store?.nameStore || '-'}}</span>
					</div>
				</div>

				<div class="wrap-fields">
					<div>
						<span class="label">Costo</span>
						<span class="output">{{ invoiceDetail.costItemType || '-'}}</span>
					</div>
				</div>
			</div>

			<div class="wrap-fields" *ngIf="option == 2">
				<div>
					<span class="label">Nombre del Lote</span>
					<span class="output">{{ invoiceDetail.lot?.nameLot || '-'}}</span>
				</div>
			</div>
			<div class="wrap-fields">
				<div>
					<span class="label">Cantidad</span>
					<span class="output">{{ invoiceDetail.amountInvoiceDetail || '-'}}</span>
				</div>
			</div>
			<div class="wrap-fields">
				<div>
					<span class="label">Total del Item</span>
					<span class="output">{{ invoiceDetail.totalInvoiceDetail || '-'}}</span>
				</div>
			</div>
			<div class="wrap-fields">
				<div>
					<span class="label">Observaciónes</span>
					<span class="output">{{ invoiceDetail.note || '-'}}</span>
				</div>
			</div>
        </div>
    </div>

	<ng-template #template>
		<div class="modal-body text-center">
			<div class="dialog-title">Confirmación </div>
			<div class="dialog-message">¿Estas seguro que quieres eliminar esta factura?</div>
			<div class="dialog-options">
				<button class="btn-text green" type="button" (click)="delete()">
					<div class="text">Si</div>
				</button>
				<button class="btn-text red" type="button" (click)="decline()" >
					<div class="text">No</div>
				</button>
			</div>
		</div>
	</ng-template>
	<!---->
	`
})

export class InvoiceDetailReadComponent implements OnInit {
	invoiceDetail: InvoiceDetail = new InvoiceDetail();
	modalRef: BsModalRef;
	confirmDelete = true;
	option: number;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private invoiceDetailService: InvoiceDetailService,
		private modalService: BsModalService,
		private notificationService: NotificationService,
	) { }

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
			this.invoiceDetailService.getById(params['invoiceDetailId']).subscribe( data => {
				this.invoiceDetail = data['result'];
				this.option = this.invoiceDetail.invoice.provider.providerType.id;
			});
		});
	}

	openModal(template: TemplateRef<any>) {
		this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
	}

	decline(): void {
		this.modalRef.hide();
	}

	update() {
		this.router.navigate(['./update'], {relativeTo: this.activatedRoute});
	}

	delete(this) {
		console.log(13);
		this.invoiceDetailService.delete(this.invoiceDetail.id).subscribe( any => {
			this.notificationService.sucessDelete('Detalle');
			this.modalRef.hide();
			// this._location.back();
			console.log(this.activatedRoute);
			// let url = this.location.back();
			// this.router.navigate([url.substr(0, url.lastIndexOf('/'))]);
		}, err =>  {
			this.notificationService.error(err);
			this.modalRef.hide();
		});
	}
}


