import { Component, OnInit, Input } from '@angular/core';
import { MediaFile } from '../gallery/media-file';
import { ToolBase } from 'app/shared/tool-ns/tool/tool-base';
import { IconTool } from 'app/shared/tool-ns/tool/tool-icon';
import { TableColumn } from 'app/shared/table-ns/table-ns-column';

@Component({
	selector: 'media-pdf-management',
	templateUrl: './media-pdf-management.component.html',
	styleUrls: ['./media-pdf-management.component.css']
})
export class MediaPdfManagementComponent implements OnInit {

	@Input() pdfs: MediaFile[];
	title: string;
	notFound: string;

	tools: ToolBase<any>[] = [
		new IconTool({
			title: 'update',
			icon: 'update',
			clicked: this.updatePdf.bind(this)
		}),
		new IconTool({
			title: 'delete',
			icon: 'delete',
			clicked: this.deletePdf.bind(this)
		})
	];

	cols: TableColumn[] = [
		new TableColumn({key: 'name', proportion: 3}),
		new TableColumn({name: 'size (KB)', key: 'size', proportion: 1})
	];
	constructor() { }

	ngOnInit() {
		this.title = 'Gestión de Pdf';
		this.notFound = 'No se encontraron pdfs asociados';
	}

	deletePdf(this) {

	}

	updatePdf(this) {

	}
}
