
import { BsModalService } from 'ngx-bootstrap/modal';
import { ProviderService } from './provider.service';
import { Location } from '@angular/common';
import { Component, OnInit, TemplateRef} from '@angular/core';
import { Router, ActivatedRoute} from '@angular/router';
import { Provider } from '../../core/models/provider';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { NotificationService } from '../../core/utils/notification/notification.service';

@Component({
	template: `
		<h3 class="title">Detalles del Proveedor</h3>
		<div class="tool-bar both-side">
			<div class="right row">
				<button class="btn-icon" title="Update" type="button" (click)="update()">
					<i class="material-icons">edit</i>
				</button>
				<button class="btn-icon" title="Delete" type="button" (click)="openModal(template)">
					<i class="material-icons">delete</i>
				</button>
			</div>
		</div>

		<div class="answer">
			<div class="fieldset">
				<div class="legend">Datos del Proveedor</div>

				<ng-template [ngIf]= " selected == 'Vendedor'">

					<div class="wrap-fields">
						<div>
							<span class="label">Estatus</span>
							<span class="output">{{provider.statusProvider?.name || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Tipo del Proveedor</span>
							<span class="output">{{provider.providerType?.nameProviderType || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Nombre del Vendedor</span>
							<span class="output">{{provider.nameProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">RUC</span>
							<span class="output">{{provider.nitProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Dirección</span>
							<span class="output">{{provider.addressProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Número de Teléfono</span>
							<span class="output">{{provider.numberProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Correo Electrónico</span>
							<span class="output">{{provider.emailProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Nombre de Contacto</span>
							<span class="output">{{provider.contactNameProvider || '-'}}</span>
						</div>
					</div>

				</ng-template>

				<ng-template [ngIf]= " selected == 'Cosechador'">
					<!--<div class="wrap-fields">
						<div>
							<span class="label">Estatus</span>
							<span class="output" *ngIf="provider.deleted">Inactivo</span>
							<span class="output" *ngIf="!provider.deleted">Activo</span>
						</div>
					</div>-->

					<div class="wrap-fields">
						<div>
							<span class="label">Estatus</span>
							<span class="output">{{provider.statusProvider?.name || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Tipo del Proveedor</span>
							<span class="output">{{provider.providerType?.nameProviderType || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Nombre del Cosechador</span>
							<span class="output">{{provider.nameProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">DNI</span>
							<span class="output">{{provider.nitProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Dirección</span>
							<span class="output">{{provider.addressProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Número de Teléfono</span>
							<span class="output">{{provider.numberProvider || '-'}}</span>
						</div>
					</div>

					<div class="wrap-fields">
						<div>
							<span class="label">Correo Electrónico</span>
							<span class="output">{{provider.emailProvider || '-'}}</span>
						</div>
					</div>
				</ng-template>

			</div>
		</div>

		<ng-template #template>
			<div class="modal-body text-center">
				<div class="dialog-title">Confirmación </div>
				<div class="dialog-message">¿Estas seguro que quieres eliminar este lote?</div>
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
	`
})
export class ProviderReadComponent implements OnInit {
	modalRef: BsModalRef;
	provider = new Provider();
	selected = '';

	constructor(
		private router: Router,
		private location: Location,
		private activatedRoute: ActivatedRoute,
		private providerService: ProviderService,
		private modalService: BsModalService,
		private notificationService: NotificationService,
	) { }

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
				this.providerService.getById(params['providerId']).subscribe(
					data => {
						this.provider = data['result'];
						this.selected = this.provider.providerType.nameProviderType;
				});
		});
	}

	update() {
		this.router.navigate(['./update'], {relativeTo: this.activatedRoute});
	}

	decline(): void {
		this.modalRef.hide();
	}

	openModal(template: TemplateRef<any>) {
		this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
	}

	delete() {
		this.providerService.delete(this.provider.id).subscribe( any => {
			this.notificationService.sucessDelete('Proveedor');
			let url = this.location.path();
			this.router.navigate([url.substr(0, url.lastIndexOf('/'))]);
			},  err => this.notificationService.error(err));
		this.modalRef.hide();
	}
}
