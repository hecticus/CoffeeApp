
export class Status {
	id: number;
	name: string;
	description: string;

	constructor()
	constructor(id?: number, name?: string) {
		this.id = id;
		this.name = name;
	}
}
