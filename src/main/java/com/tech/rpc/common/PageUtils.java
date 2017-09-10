package com.tech.rpc.common;


import com.google.gson.annotations.Expose;



/**    
 *     
 * 项目名称：fmallApp    
 * 类名称：PageUtils    
 * 类描述：    
 * 创建人：ex_kjkfb_lvrz    
 * 创建时间：2014年3月10日 上午10:38:47    
 * 修改人：ex_kjkfb_lvrz    
 * 修改时间：2014年3月10日 上午10:38:47    
 * 修改备注：    
 * @version     
 *     
 */
public class PageUtils {
	public static final int PAGE_SIZE = 10;
	public static final int CURRENT_PAGE = 1;
	
	@Expose
	private int currentPage;//当前页
	@Expose
	private int pageSize;
	@Expose
	private int totalPage;
	@Expose
	private int totalRecord;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
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
	
}
