package com.torrow.school.service;
import org.springframework.web.multipart.MultipartFile;
import com.torrow.school.entity.TbMessage;
import com.torrow.school.util.PageBean;
/**
 * 留言业务类
 * 
 * @author 张金高
 *
 *         2017年10月8日下午5:16:57
 */

public interface TbMessageService {
	// 删除留言
	int deleteByPrimaryKey(Integer meId);
	// 添加留言
	int insert(TbMessage record);
	// 根据id得到单个留言信息
	TbMessage selectByPrimaryKey(Integer meId);
	// 修改留言
	int updateByPrimaryKey(TbMessage record);
	// 得到所有留言，分页
	PageBean<TbMessage> findPage(int currentPage,String inquiry,int pageSize);
	// 留言回复
	int reply(int id, String meReply);
	// 富文本上传图片
	String uploadImg(MultipartFile file, String path) throws Exception;
}