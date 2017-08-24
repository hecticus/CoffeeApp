import { Md5 } from 'ts-md5/dist/md5';

export class Global {

	public static readonly EMAIL_PATTERN: string = '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$';
	public static readonly URL_PATTERN: string = '';

	public static isEmpty(obj: Object) : boolean {
		return Object.keys(obj).length === 0 && obj.constructor === Object;
	}

	public static toMD5 (value: string) {
		return Md5.hashStr(value);
	}

	public static secToMmss(value) {
		value = parseInt(value, 10);
		let minutes = Math.floor(value / 60) % 60;
		let seconds = value % 60;
		return [minutes, seconds]
			.map(t => t < 10 ? '0' + t : t)
			.join(':');
	}

}
