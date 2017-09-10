package com.tech.rpc.common;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**    
 *     
 * 项目名称：fmallWeb    
 * 类名称：Page    
 * 类描述：    
 * 创建人：ex_kjkfb_lvrz    
 * 创建时间：2014年3月10日 上午10:34:05    
 * 修改人：ex_kjkfb_lvrz    
 * 修改时间：2014年3月10日 上午10:34:05    
 * 修改备注：    
 * @version     
 *     
 */
public class Page<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2301619997327806687L;
	private int currentPage = 1;//当前页
	private int pageSize = 20;
	private int totalPage;
	private int totalRecord;
	private int offset;
	
	private Map<String,Object> params;
	private  T paraObject;
	private List<T> resultList;
	private List<Integer> pageNums;
	
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
	    this.currentPage = currentPage;
	}
	public void setCurrentPage(int currentPage,int pageSize) {
        currentPage = currentPage <= 0? 1 :currentPage;
        this.currentPage = currentPage;
        this.pageSize =  pageSize <= 0 ? 10 : pageSize; 
        int offset = (currentPage-1)*this.pageSize;
        this.offset=offset;
        
    }
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		int totalPage = totalRecord%getPageSize()==0?totalRecord/getPageSize():totalRecord/pageSize+1;
		this.setTotalPage(totalPage);
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public T getParaObject() {
		return paraObject;
	}
	public void setParaObject(T paraObject) {
		this.paraObject = paraObject;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}


	public List<Integer> getPageNums() {
		int startPage = 0;
		int endPage = 0;
		int showPage = 6;
		if(this.totalPage<=showPage) {
			startPage = 1;
			if(this.totalPage<=startPage){
				endPage = startPage;
			}else {
				endPage = this.totalPage;
			}
		}else {
			if(currentPage<=(showPage/2)) {
				startPage = 1;
				endPage = showPage;
				if(endPage>=this.totalPage){
					endPage = this.totalPage;
				}
			} else {
				startPage = this.totalPage-(showPage/2);
				endPage = this.totalPage+(showPage/2)-1;
				if(endPage>=this.totalPage) {
					endPage = this.totalPage;
					if(endPage - startPage<=showPage) {
						startPage = endPage -showPage +1;
					}
				}
			}
		}

		List<Integer> pageNums = new ArrayList<>();
		for(int i=startPage;i<=endPage;i++) {
			pageNums.add(i);
		}
		this.pageNums=pageNums;
		return pageNums;
	}

	public void setPageNums(List<Integer> pageNums) {
		this.pageNums = pageNums;
	}


	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", pageSize=" + pageSize
				+ ", totalPage=" + totalPage + ", totalRecord=" + totalRecord
				+ ", offset=" + offset + ", paraObject=" + paraObject
				+ ", resultList=" + (resultList != null ? resultList
						.subList(0, Math.min(resultList.size(), 10)):"") + "]";
	}
	
}
