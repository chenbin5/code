package com.example;

import com.alibaba.fastjson.JSON;
import com.chenb.entry.Depot;
import com.chenb.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DepotService depotService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello world.....";
    }

    @RequestMapping("/queryDepot")
    public String queryDepot() {
      List<Depot> list = depotService.queryDepotList();
      return JSON.toJSONString(list);
    }
}
