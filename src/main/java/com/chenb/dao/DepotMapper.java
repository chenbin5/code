package com.chenb.dao;

import com.chenb.entry.Depot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepotMapper {

    public List<Depot> queryDepotList();
}
