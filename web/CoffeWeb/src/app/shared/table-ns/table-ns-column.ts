export class TableColumn {
	name: string;
  	key: string;
  	proportion: number;

	constructor(options: {
		name?: string,
		key?: string,
		proportion?: number
	} = {}) {
		this.name = options.name || options.key;
		this.key = options.key;
		this.proportion = options.proportion;
	}
}