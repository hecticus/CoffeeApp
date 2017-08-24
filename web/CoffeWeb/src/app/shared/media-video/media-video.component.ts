import { Component, OnInit, Input } from '@angular/core';
import { MediaFile } from '../media-upload/media-file';

@Component({
	selector: 'media-video',
	templateUrl: './media-video.component.html',
	styleUrls: ['./media-video.component.css']
})
export class MediaVideoComponent implements OnInit {

	@Input() videos: MediaFile[];

	title: string;
	notFound: string;
	selectedVideo: MediaFile;
	selectedCanvas: MediaFile;

	constructor() { }

	ngOnInit() {
		this.title = 'Gestión de Video';
		this.notFound = 'No se encontraron videos asociados';
	}

	openVideo (video: MediaFile) {
		this.selectedVideo = video;
	}

	openCanvas(video: MediaFile) {
		this.selectedCanvas = video;
	}

	onCapture(video: MediaFile) {
		if (video === null) {
			this.selectedCanvas = undefined;
		} else {
			console.log("video duration "+ video.duration);
			console.log("video thumb "+ video.thumbnail);
		}
	}

}
