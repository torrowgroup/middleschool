package com.torrow.school.dao;

import com.torrow.school.entity.TbResource;
import java.util.List;

public interface TbResourceDao {
    int deleteByPrimaryKey(Integer reId);

    int insert(TbResource record);

    TbResource selectByPrimaryKey(Integer reId);

    List<TbResource> selectAll();

    int updateByPrimaryKey(TbResource record);
}