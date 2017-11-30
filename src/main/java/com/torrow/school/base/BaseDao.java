package com.torrow.school.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.web.multipart.MultipartFile;

import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;

/**
 * @author 张金高
 * @param <T>
 *            这个泛型通配符用来指定实体类
 */
public class BaseDao<T> extends SqlSessionDaoSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger log;
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		// 获得log对象
		if (log == null) {
			log = Logger.getLogger(this.getClass());
		}

		// 获得子类的泛型参数的类型
		Type type = this.getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			type = type.getClass().getSuperclass().getGenericSuperclass();
		}
		clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
	}

	// 这是为了让父类获得SqlSessionFactory实例以便获得SqlSession，该实例已在spring-mybatis.xml配置
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	// 得到***Mapper.xml的命名空间
	public final String getNameSpace() {
		return clazz.getName().replaceFirst("entity", "dao") + "Dao";
	}

	public final int deleteEntity(int id) {
		return getSqlSession().delete(this.getNameSpace() + ".deleteByPrimaryKey", id);
	}

	/**
	 * 插入一条记录
	 * 
	 * @param t
	 *            对象实例
	 * @return 返回受影响的行数
	 */
	public final int insertEntity(T record) {
		return getSqlSession().insert(this.getNameSpace() + ".insert", record);
	}

	/**
	 * @param id
	 * @return 根据id返回一个实例
	 */
	public final T selectOneEntity(int id) {
		return getSqlSession().selectOne(this.getNameSpace() + ".selectByPrimaryKey", id);
	}

	/**
	 * @return 返回所有记录
	 */
	public final List<T> selectAllEntity() {
		return getSqlSession().selectList(this.getNameSpace() + ".selectAll");
	}

	/**
	 * 更新一条记录
	 * 
	 * @param t
	 *            对像实例
	 * @return 受影响的行数
	 */
	public final int updateEntity(T record) {
		return getSqlSession().update(this.getNameSpace() + ".updateByPrimaryKey", record);
	}

	/**
	 * 分页查询
	 * @param currentPage 当前页码
	 * @return
	 */
	public final PageBean<T> pageCut(int currentPage, int pageSize) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// int pageSize = 2;
		int totalCount = getSqlSession().selectOne(this.getNameSpace() + ".selectCount");// 得到总记录数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		map.put("start", (currentPage - 1) * pageSize);
		map.put("size", pageSize);
		List<T> lists = getSqlSession().selectList(this.getNameSpace() + ".findByPage", map);// 得到所有的记录
		PageBean<T> pageBean = new PageBean<T>(currentPage, pageSize, lists, num.intValue(), totalCount);
		return pageBean;
	}

	// 上传图片,返回图片名
	public String uploadP(MultipartFile picture, String path) throws Exception {
		String fileName = picture.getOriginalFilename();
		fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);// uuid+文件扩展名避免重名,中文名等问题
		File uploadFile = new File(path, fileName);
		picture.transferTo(uploadFile);
		return fileName;
	}

	// 上传文件,返回文件名
	public String uploadF(MultipartFile file, String path) throws Exception {
		String fileName = file.getOriginalFilename();
		File uploadFile = new File(path, fileName);
		file.transferTo(uploadFile);
		return fileName;
	}
	
	public void download(HttpServletRequest request,HttpServletResponse response,int id) throws Exception{  
		//根据ID来查找相应的实体类对应的字段信息，这里指的是文件地址
    	TbResource tb = getSqlSession().selectOne(this.getNameSpace() + ".selectByPrimaryKey", id);
    	//模拟文件，myfile.txt为需要下载的文件  
        String fileName = request.getSession().getServletContext().getRealPath("/static/uploadimg")+"/" + tb.getReContent(); 
        //获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));  
        //假如以中文名下载的话  
        String filename = tb.getReContent();  
        //转码，免得文件名中文乱码  
        filename = URLEncoder.encode(filename,"UTF-8");  
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();  
    }  

}
