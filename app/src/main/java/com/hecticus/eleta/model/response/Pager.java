
package com.hecticus.eleta.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pager {

    @SerializedName("totalEntitiesPerPage")
    @Expose
    private Integer totalEntitiesPerPage;
    @SerializedName("totalEntities")
    @Expose
    private Integer totalEntities;
    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("endIndex")
    @Expose
    private Integer endIndex;

    public Integer getTotalEntitiesPerPage() {
        return totalEntitiesPerPage;
    }

    public void setTotalEntitiesPerPage(Integer totalEntitiesPerPage) {
        this.totalEntitiesPerPage = totalEntitiesPerPage;
    }

    public Integer getTotalEntities() {
        return totalEntities;
    }

    public void setTotalEntities(Integer totalEntities) {
        this.totalEntities = totalEntities;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "totalEntitiesPerPage=" + totalEntitiesPerPage +
                ", totalEntities=" + totalEntities +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", pages=" + pages +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
