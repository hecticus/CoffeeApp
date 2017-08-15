import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'bt-toogle',
    templateUrl: './button-toogle.component.html',
    styleUrls: ['../button.component.css'],
})
export class ButtonToogleComponent {
    @Input() title: string;
    @Input() toogle: boolean;
    @Input() iconOff: string;
    @Input() iconOn: string;
    @Output() clicked = new EventEmitter();

    onClicked($event){
        this.toogle = !this.toogle;
        this.clicked.emit();
    }

    setToogle(toogle){
        this.toogle = toogle;
    }
}