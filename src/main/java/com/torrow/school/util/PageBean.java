package com.torrow.school.util;
import java.util.List;
/**
 * 分页实体类
 * @author 
 * @Date2016年12月13日 上午9:40:10
 */

/**
 * @author 安李杰
 *
 * @2017年10月6日下午5:33:32
 */
public class PageBean<T> {

    private int currPage;//当前页数
    private int pageSize;//每页显示的记录数
    private int totalCount;//总记录数
    private int totalPage;//总页数
    private List<T> lists;//每页的显示的数据

    public PageBean() {
        super();
    }

    public PageBean(int currPage,int pageSize,List<T> lists,int totalPage,int totalCount){
    	this.currPage = currPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.lists = lists;
    }

	public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

	@Override
	public String toString() {
		return "PageBean [currPage=" + currPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", lists=" + lists + "]";
	}

}