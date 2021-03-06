package com.example;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.chenb.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AlipayControllerOld {

    @Autowired
    private AlipayService alipayService;
    /**
     * web 订单支付
     */
    @GetMapping("getPagePay")
    public Map<Object, Object> getPagePay() throws Exception{
        /** 模仿数据库，从后台调数据*/
        String outTradeNo = "19960310621211";
        Integer totalAmount = 1;
        String subject = "苹果28";

        String pay = alipayService.webPagePay(outTradeNo, totalAmount, subject);
        System.out.println(pay);

        Map<Object, Object> pays = new HashMap<>();
        pays.put("pay", pay);

        return pays;
    }

    /**
     * app 订单支付
     */
    @GetMapping("getAppPagePay")
    public String getAppPagePay() throws Exception{
        /** 模仿数据库，从后台调数据*/
        String outTradeNo = "131233";
        Integer totalAmount = 1000;
        String subject = "天猫超市012";

        String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);

        Object json = JSONObject.toJSON(pay);

        System.out.println(json);

        return JSONObject.toJSONString(json);
    }

    /**
     * 交易查询
     */
    @PostMapping("aipayQuery")
    public String alipayQuery() throws Exception{
        /**调取支付订单号*/
        String outTradeNo = "13123";

        String query = alipayService.query(outTradeNo);

        Object json = JSONObject.toJSON(query);

        /*JSONObject jObject = new JSONObject();
        jObject.get(query);*/
        return JSONObject.toJSONString(json);
    }

    /**
     * 退款
     * @throws AlipayApiException
     */
    @GetMapping("alipayRefund")
    public String alipayRefund(
            @RequestParam("outTradeNo")String outTradeNo,
            @RequestParam(value = "outRequestNo", required = false)String outRequestNo,
            @RequestParam(value = "refundAmount", required = false)Integer refundAmount
    ) throws AlipayApiException {

        /** 调取数据*/
        //String outTradeNo = "15382028806591197";
        String refundReason = "用户不想购买";
        //refundAmount = 1;
        //outRequestNo = "22";

        String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);

        System.out.println(refund);

        return JSONObject.toJSONString(refund);
    }

    /**
     * 退款查询
     * @throws AlipayApiException
     */
    @PostMapping("refundQuery")
    public String refundQuery() throws AlipayApiException{

        /** 调取数据*/
        String outTradeNo = "13123";
        String outRequestNo = "2";

        String refund = alipayService.refundQuery(outTradeNo, outRequestNo);

        return JSONObject.toJSONString(refund);

    }

    /**
     * 交易关闭
     * @throws AlipayApiException
     */
    @PostMapping("alipayclose")
    public String alipaycolse() throws AlipayApiException{

        /** 调取数据*/
        String outTradeNo = "13123";

        String close = alipayService.close(outTradeNo);

        return JSONObject.toJSONString(close);
    }

}
