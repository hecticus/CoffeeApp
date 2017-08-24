import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
	selector: 'confirm-ns',
  	templateUrl: './confirmation-ns.component.html',
  	styleUrls: ['./confirmation-ns.component.css'],
})
export class ConfirmationComponent {
	@Input() hiddenClose: boolean;
	@Output() hiddenCloseChange = new EventEmitter();
	@Input() title: string = "Confirmation";
	@Input() message: string;
    @Output() yesClicked = new EventEmitter();
    
  	onYesClicked(){
		this.yesClicked.emit();
		this.hideDialog();
	}

	hideDialog(){
		this.hiddenClose = true;
		this.hiddenCloseChange.emit(true);
	}

	showDialog(){
		this.hiddenClose = false;
		this.hiddenCloseChange.emit(false);
	}
}