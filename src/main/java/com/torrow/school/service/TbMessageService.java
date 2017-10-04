package com.torrow.school.service;

import com.torrow.school.entity.TbMessage;
import java.util.List;

public interface TbMessageService {
    int deleteByPrimaryKey(Integer meId);

    int insert(TbMessage record);

    TbMessage selectByPrimaryKey(Integer meId);

    List<TbMessage> selectAll();

    int updateByPrimaryKey(TbMessage record);
}