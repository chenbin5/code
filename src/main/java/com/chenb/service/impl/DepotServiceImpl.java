package com.chenb.service.impl;

import com.chenb.dao.DepotMapper;
import com.chenb.entry.Depot;
import com.chenb.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepotServiceImpl implements DepotService {


    @Autowired
    private DepotMapper depotMapper;

    @Override
    public List<Depot> queryDepotList() {
        return depotMapper.queryDepotList();
    }
}
