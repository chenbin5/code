package com.chenb.modules.depot.controller;


import com.alibaba.fastjson.JSONObject;
import com.chenb.entry.Depot;
import com.chenb.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    @RequestMapping("/query")
    public String queryDepot() {
        List<Depot> list = depotService.queryDepotList();
        return JSONObject.toJSONString(list);
    }

}
