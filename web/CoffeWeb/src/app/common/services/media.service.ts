import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import { BaseService } from './base.service';
import { MediaFile } from 'app/shared/models/media-file';

@Injectable()
export class MediaService extends BaseService {

	private urlMedia: string = this.HOST + '/medias';

	constructor(private http: Http) {
		super();
	}

	getExternalMedia(url: string): Observable<File> {
		return this.http.get(url)
			.map(res => res.blob)
			.catch(this.handleError);
	}

	deleteMedias(mediaIds: number[]): Observable<any> {
		console.log(JSON.stringify({ids: mediaIds}));
		return this.http.post(this.urlMedia + '/delete', JSON.parse(JSON.stringify({ids: mediaIds})))
			.map(this.extractData)
			.catch(this.handleError);
	}

	buildMediaResponse (files: Array<MediaFile>) {
		let result: Array<any> = [];
		for (let i = 0; i < files.length; i++) {
			let file = files[i];
			result.push({name: file.name, media: file.base64});
		}
		// console.log("media "+JSON.stringify(result));
		return JSON.parse(JSON.stringify(result));
	}

	public readMedia (file: File, idMedia: number): Observable<MediaFile> {
		let reader = new FileReader();
		let sizeKB = this.toKilobytes(file.size);
		let image = new Image();
		let mediaObservable = Observable.create((observer: any) => {
			reader.onload = function() {
				image.onload = function() {
					console.log("yei!!");
					console.log("image "+image.width+" x "+image.height);
				};
				let media: MediaFile = {
					id: idMedia,
					base64: reader.result.replace(/^data:image\/(png|jpg|jpeg|bmp|svg);base64,/, ''),
					name: file.name.replace(/\s+/g, '_'),
					size: sizeKB,
					url: reader.result
				};
				observer.next(media);
				observer.complete();
			};
		});
		reader.readAsDataURL(file);
		return mediaObservable;
	}

	public onloadMedia(file: File, idMedia: number, callback) {
		let reader = new FileReader();
		reader.onload = (event) => {
			let media: MediaFile = {
				id: idMedia,
				base64: btoa(reader.result),
				name: file.name,
				size: file.size,
				url: undefined
			};
			callback(media);
		};
		reader.readAsDataURL(file);
	}

	public toBase64 (file: File, callback) {
		let reader = new FileReader();
		reader.onload = (event) => {
			callback(reader.result);
		};
		reader.readAsDataURL(file);
	}

	public toKilobytes(value: number): number {
		return Math.round(value / Math.pow(2, 10));
	}

}
