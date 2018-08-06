export interface Pager {
	totalEntities?: number;
	totalEntitiesPerPage?: number;
	pageIndex: number;
	pageSize: number;
	pages?: number;
	startIndex?: number;
	endIndex?: number;
}
