package org.manage.model;

/**
 * @author: Diego
 * @date: 2020/7/6 9:27
 * @Des:
 */
public class BaseIn {
    Object obj;
    Integer pageIndex;
    Integer pageNumber;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
