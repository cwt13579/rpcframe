package com.tech.rpc.module.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 基础的dao
 * @author liusq
 *
 */
public class BaseDao extends JdbcDaoSupport {
	
	
    @Autowired
    public void setBaseJdbcTemplate(JdbcTemplate b2bJdbcTemplate) {
        setJdbcTemplate(b2bJdbcTemplate);
    }
    
    
    /**
     * 查询数量
     * @param sql
     * @param params
     * @return
     */
    protected int queryCnt(String sql,Object[] params){
    	return this.getJdbcTemplate().queryForInt(sql,params);
    }
    
    /**
     * 分页
     * 对排序支持不够，会出现数据减少问题，推荐不使用
     * @param sql
     * @param params
     * @param currentpage
     * @param pagesize
     * @return
     */
    @Deprecated
    protected List<Map<String,Object>> basePage(String sql,Object[] params,int currentpage,int pagesize){
    	
    	StringBuffer pageSql = new StringBuffer("");
		pageSql.append(" SELECT * FROM (");
		pageSql.append(" SELECT A.*,ROWNUM RN FROM (");
		pageSql.append(sql).append(" ) A ");
		pageSql.append(" WHERE ROWNUM <=? ) B WHERE B.RN >=?");
		return this.getJdbcTemplate().queryForList(pageSql.toString(), this.setPageArray(params, currentpage, pagesize));
    }
    

    /**
     * 分页 +排序
     * 推荐5星级
     * @param sql
     * @param params
     * @author xuek
     * @param currentpage
     * @param pagesize
     * @return
     */
    protected List<Map<String,Object>> basePageOrder(String sql,Object[] params,int currentpage,int pagesize)throws ServiceException
    {
    	try{
			StringBuffer pageSql = new StringBuffer("");
			pageSql.append(" SELECT * FROM (");
			pageSql.append(" SELECT A.*,ROWNUM RN FROM (");
			pageSql.append(sql).append(" ) A ");
			pageSql.append(") B WHERE B.RN<? AND B.RN >=?");
			return this.getJdbcTemplate().queryForList(pageSql.toString(),setPageArray(params, currentpage, pagesize));
    	}catch(Exception e){
    		logger.error("BaseDao.basePageOrder() error" + e);
    		throw new ServiceException("调用分页方法basePageOrder异常",e);
    	}
    }
    
	/**
	 * 处理分页数组参数
	 * @param parPage
	 * @param page
	 * @param pagesize
	 * @return Object[]
	 */
	private Object[] setPageArray(Object[] parPage,int page,int pagesize){
		
		int beforeNum = 0;
		int afterNum = 0;
		
	 	beforeNum =  page * pagesize;
	 	
	 	afterNum = beforeNum - pagesize + 1;
		
	 	Object[] obj = null; 
	 	
	 	if(parPage != null){
	 		obj = new Object[parPage.length+2];
	 		for(int i=0;i<parPage.length;i++){
	 			obj[i] = parPage[i];
	 		}
		 	obj[parPage.length] = beforeNum;
		 	obj[parPage.length+1] = afterNum;
	 	}else{
	 		obj = new Object[2];
		 	obj[0] = beforeNum;
		 	obj[1] = afterNum;
	 	}
	 	
	 	return obj;
		
	}
}
