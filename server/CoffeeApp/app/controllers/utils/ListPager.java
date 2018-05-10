package controllers.utils;

import java.util.List;

/**
 * Created by nisa on 16/05/17.
 */
public class ListPager<E> {
    public List<E> entities; // entites per page

    public class Pager {
        public Integer totalEntities; // total entities for all pages
        public Integer pageIndex; // current page
        public Integer pageSize; // size per page
        public Integer pages;   // amount of pages
        public final Integer startIndex = 0;
        public Integer endIndex;

        public Pager(Integer entitiesTotal, Integer pageIndex, Integer pageSize){
            this.totalEntities = entitiesTotal;
            this.pageIndex = pageIndex < 0 ? 0 : pageIndex;
            this.pageSize = pageSize < 1 ? this.totalEntities : pageSize;
            this.pages = (int) Math.ceil((double)this.totalEntities / this.pageSize);
            this.endIndex = this.pages - 1;
        }
    }
    public Pager pager;

    public ListPager(List<E> entities, Integer totalEntities, Integer pageIndex, Integer pageSize) {
        this.entities = entities;
        pager = new Pager(totalEntities, pageIndex, pageSize);
    }
}