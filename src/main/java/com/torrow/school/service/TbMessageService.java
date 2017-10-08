package com.torrow.school.service;

import java.util.List;

import com.torrow.school.entity.TbMessage;
import com.torrow.school.util.PageBean;
/**
 * 留言业务类
 * 
 * @author 张金高
 *
 * 2017年10月8日下午5:16:57
 */

public interface TbMessageService {
    int deleteByPrimaryKey(Integer meId);

    int insert(TbMessage record);

    //根据id得到单个留言信息
    TbMessage selectByPrimaryKey(Integer meId);

    List<TbMessage> selectAll();

    int updateByPrimaryKey(TbMessage record);

    //得到所有留言，分页
	PageBean<TbMessage> findPage(int currentPage);
}