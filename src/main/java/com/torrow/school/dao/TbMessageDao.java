package com.torrow.school.dao;

import com.torrow.school.entity.TbMessage;
import java.util.List;

public interface TbMessageDao {
    int deleteByPrimaryKey(Integer meId);

    int insert(TbMessage record);

    TbMessage selectByPrimaryKey(Integer meId);

    List<TbMessage> selectAll();

    int updateByPrimaryKey(TbMessage record);
}