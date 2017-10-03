package com.torrow.school.service;

import com.torrow.school.entity.TbResource;
import java.util.List;

public interface TbResourceService {
    int deleteByPrimaryKey(Integer reId);

    int insert(TbResource record);

    TbResource selectByPrimaryKey(Integer reId);

    List<TbResource> selectAll();

    int updateByPrimaryKey(TbResource record);
}