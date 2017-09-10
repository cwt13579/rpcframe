package com.tech.rpc.common;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 *
 * 项目名称：fmallApp
 * 类名称：PageInterceptor
 * 类描述：
 * 创建人：ex_kjkfb_lvrz
 * 创建时间：2014年3月10日 上午10:10:39
 * 修改人：ex_kjkfb_lvrz
 * 修改时间：2014年3月10日 上午10:10:39
 * 修改备注：
 * @version
 *
 */
@Intercepts({
@Signature(method="prepare",type=StatementHandler.class,args={Connection.class})})
public class PageInterceptor implements Interceptor{

	private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
	private String dataBaseType;//数据库类型 不同数据库不同的分页
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();
        if (obj instanceof Page<?>) {
            Page<?> page = (Page<?>) obj;

            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            this.setTotalRecord(page, mappedStatement, connection);
            String pageSql = this.getPageSql(page, sql);
            logger.info("SQL:" + pageSql);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }

	@Override
	public Object plugin(Object arg0) {
	    if(arg0 instanceof StatementHandler)
	    {
	    	return Plugin.wrap(arg0, this);
	    }
	    return arg0;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		this.dataBaseType = properties.getProperty("dataBaseType");
	}
	private String getPageSql(Page<?> page,String sql)
	{
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(this.dataBaseType)){
			return getMysqlPageSql(page,sqlBuffer);
			    }
		    else if("oracle".equalsIgnoreCase(this.dataBaseType)){
		    	return getOraclePageSql(page,sqlBuffer);
		}
		return sqlBuffer.toString();
     }

	/**
	 *        * 获取Mysql数据库的分页查询语句       * @param page 分页对象 
	 *      * @param sqlBuffer 包含原sql语句的StringBuffer对象 
	 *      * @return Mysql数据库分页语句       
	 */
	private String getMysqlPageSql(Page<?> page,StringBuffer sqlBuffer)
	   {
		//计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		//指定了页数按优先按页数查询 若没指定按起始位置查询
		int offset=page.getCurrentPage()!=0?(page.getCurrentPage()-1)*page.getPageSize():page.getOffset();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	     }
	    /** 
	     * 获取Oracle数据库的分页查询语句 
	     * @param page 分页对象 
	     * @param sqlBuffer 包含原sql语句的StringBuffer对象 
	     * @return Oracle数据库的分页查询语句 
	     */
	private String getOraclePageSql(Page<?> page,StringBuffer sqlBuffer)
	  {
		//计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
				int offset = (page.getCurrentPage()-1)*page.getPageSize()+1;
				StringBuffer pageSql = new StringBuffer();
				pageSql.append(" SELECT * FROM (");
				pageSql.append(" SELECT A.*,ROWNUM RN FROM (");
				pageSql.append(sqlBuffer).append(" ) A ");
				pageSql.append(") B WHERE B.RN<"+(offset+page.getPageSize())+" AND B.RN >="+offset+"");

				/*  sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset+page.getPageSize());
			      sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);*/
			      return pageSql.toString();
	      }
	/** 
	     * 给当前的参数对象page设置总记录数 
	     * 
	     * @param page Mapper映射语句对应的参数对象 
	     * @param mappedStatement Mapper映射语句 
	     * @param connection 当前的数据库连接 
	     */
	private void setTotalRecord(Page<?> page,
			MappedStatement mappedStatement,Connection connection)
	{
				BoundSql boundSql = mappedStatement.getBoundSql(page);
				String sql = boundSql.getSql();
				String countSql = this.getCountSql(sql);
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),countSql,parameterMappings,page);
				//通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
				ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
				//通过connection建立一个countSql对应的PreparedStatement对象。
			      PreparedStatement pstmt = null;
			      ResultSet rs = null;
			      try {
			    	  pstmt = connection.prepareStatement(countSql);
			    	  parameterHandler.setParameters(pstmt);
			    	  rs = pstmt.executeQuery();
			    	  if(rs!=null&&rs.next())
			    	  {
			    		  page.setTotalRecord(rs.getInt(1));//总记录数
			    	  }
				} catch (SQLException e) {
					logger.error("PageInterceptor sql error",e);
					e.printStackTrace();
				}finally{
					try {
						if(pstmt!=null)
						  {
							  pstmt.close();
						  }if(rs!=null)
						  {
							  rs.close();
						  }
					} catch (Exception e2) {
						logger.error("PageInterceptor sql error",e2);
					}
				}
	}
	/** 
	     * 根据原Sql语句获取对应的查询总记录数的Sql语句 
	     * @param sql 
	     * @return 
	     */
	private String getCountSql(String sql)
	  {

		/*int index = sql.lastIndexOf("FROM");
		//logger.info("query count sql:"+sql.substring(index));
		return "SELECT COUNT(1) "+sql.substring(index);
		System.out.println(sql.substring(index));
		System.out.println("============================================");
		System.out.println("SELECT COUNT(1) FROM("+sql+") temp");
		*/
		return "SELECT COUNT(1) FROM("+sql+") temp";
		}


	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}
}
