package controllers.utils;

import com.avaje.ebean.PagedList;

/**
 * Created by nisa on 16/05/17.
 */
public class Pager {
    public Integer totalEntities; // total entities for table
    public Integer totalEntitiesPerPage; // total entities for page
    public Integer pageIndex; // current page
    public Integer pageSize; // size per page
    public Integer pages;   // amount of pages
    public final Integer startIndex = 0;
    public Integer endIndex;

    public Pager(PagedList pagedList) {
        this.totalEntitiesPerPage = pagedList.getList().size();
        this.totalEntities = pagedList.getTotalRowCount();
        this.pageIndex = pagedList.getPageIndex();
        this.pageSize = pagedList.getPageSize();
        this.pages = pagedList.getTotalPageCount();
        this.endIndex = this.pages - 1;
    }
}