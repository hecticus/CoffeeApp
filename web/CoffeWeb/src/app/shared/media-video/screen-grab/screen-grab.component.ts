import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { MediaFile } from '../../media-upload/media-file';
import { Global } from 'app/utils/global';

@Component({
	selector: 'screen-grab',
	templateUrl: './screen-grab.component.html',
	styleUrls: ['./screen-grab.component.css']
})
export class ScreenGrabComponent implements OnInit {

	@Input() video: MediaFile;
	@Output() captured = new EventEmitter();
	@ViewChild('videoPlay') videoRef: ElementRef;
	@ViewChild('canvasCapture') canvasRef: ElementRef;

	videoTitle: string;
	canvasTitle: string;

	private canvasElem: HTMLCanvasElement;

	constructor() { }

	ngOnInit() {
		this.videoTitle = 'Video Seleccionado';
		this.canvasTitle = 'Vista Previa';
		this.canvasElem = this.canvasRef.nativeElement;
		console.log("src "+this.video.url);
	}

	onMetaData(event, video) {
		let duration = video.duration;
		this.video.duration = Global.secToMmss(duration);
		video.currentTime = (duration > 5) ? 5 : duration / 3;
		// this.captured.emit(this.video);
	}

	onPlayThrough(event, video) {
		let canvasElem: HTMLCanvasElement = this.canvasRef.nativeElement;
		let context: CanvasRenderingContext2D = canvasElem.getContext('2d');
		context.drawImage(video, 0, 0, canvasElem.width, canvasElem.height);
		// let imageData = context.getImageData(0, 0, canvasElem.width, canvasElem.height);
		// console.log("imagaData " + imageData);
		// context.fillStyle="#FF0000";
		// context.fillRect(10,10,30,30);
		// this.captureImage(canvasElem, context);
		this.captureImageTemporal();
	}

	closeScreenGrab() {
		this.captured.emit(null);
	}

	captureImage(canvas: HTMLCanvasElement, context: CanvasRenderingContext2D) {
		let image = new Image();
		// image.setAttribute('crossOrigin', 'anonymous');
		image.src = this.canvasElem.toDataURL();
		console.log("image " + image);
	}

	captureImageTemporal() {
		this.video.thumbnail = 'http://lorempixel.com/220/160/sports/1/';
		if (this.video.duration !== undefined) {
			this.captured.emit(this.video);
		}
	}

}
