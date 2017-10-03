package com.torrow.school.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @author 张金高
 * @param <T> 这个泛型通配符用来指定实体类
 */
public class BaseDao<T> extends SqlSessionDaoSupport 
             implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Logger log; 
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		//获得log对象
		if (log == null) {
			log = Logger.getLogger(this.getClass());
		}

		//获得子类的泛型参数的类型
		Type type = this.getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			type = type.getClass().getSuperclass().getGenericSuperclass();
		}
		clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
	}
    
    //这是为了让父类获得SqlSessionFactory实例以便获得SqlSession，该实例已在spring-mybatis.xml配置
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
         super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    //得到***Mapper.xml的命名空间
    public final String getNameSpace(){
    	return  clazz.getName().replaceFirst("entity", "dao")+"Dao";
    }
    
    public final int deleteEntity(int id){
    	return getSqlSession().delete(this.getNameSpace()+".deleteByPrimaryKey" , id);
    }
    /**
     * 插入一条记录
     * @param t 对象实例
     * @return 返回受影响的行数
     */
    public final int insertEntity(T record){
    	return getSqlSession().insert(this.getNameSpace()+".insert" , record);
    }
    /**
     * @param id 
     * @return 根据id返回一个实例
     */
	public final T selectOneEntity(int id){
    	return getSqlSession().selectOne(this.getNameSpace()+".selectByPrimaryKey" , id);
    }
	/**
	 * @return 返回所有记录
	 */
    public final List<T> selectAllEntity(){
    	return getSqlSession().selectList(this.getNameSpace()+".selectAll");
    }
    /**
     * 更新一条记录
     * @param t 对像实例
     * @return 受影响的行数
     */
    public final int updateEntity(T record){
    	return getSqlSession().update(this.getNameSpace()+".updateByPrimaryKey", record);
    }
    
}
