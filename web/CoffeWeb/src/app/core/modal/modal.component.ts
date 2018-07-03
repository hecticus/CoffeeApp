import { Component, OnInit, Input, Output, ContentChild, EventEmitter } from '@angular/core';
import { ModalContentDirective } from './ModalContenDirective';

@Component({
	selector: 'app-modal',
	templateUrl: './modal.component.html',
	styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

	@Input() closed: boolean;
	@Output() closedChange = new EventEmitter();
	@ContentChild(ModalContentDirective) public content: ModalContentDirective;

	ngOnInit(): void {	}

	close() {
		this.closed = true;
		this.closedChange.emit(this.closed);
	}

	open() {
		this.closed = false;
		this.closedChange.emit(this.closed);
	}

}
