package com.robin.eduorderboot.controller;

import com.robin.eduorderboot.entity.PayOrder;
import com.robin.eduorderboot.entity.PayOrderRecord;
import com.robin.eduorderboot.entity.UserCourseOrder;
import com.robin.eduorderboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("saveOrder")
    public String saveOrder(String orderNo, String user_id, String course_id, String activity_course_id, String source_type, Double price) {
        System.out.println("保存支付订单 = " + orderNo);
        orderService.saveOrder(orderNo, user_id, course_id, activity_course_id, source_type);
        // 创建订单记录
        PayOrderRecord por = new PayOrderRecord();
        por.setOrderNo(orderNo);// 订单号
        por.setType("CREATE"); //操作类型：CREATE\|PAY\|REFUND...
        por.setPaidAmount(price);
        por.setFromStatus("0");
        por.setToStatus("1");
        por.setCreatedBy(user_id);
        por.setCreatedAt(new Date());
        System.out.println("创建订单记录 = " + orderNo);
        orderService.saveOrderRecord(por);
        return orderNo;
    }

    @GetMapping("updateOrder")
    public Integer updateOrder(String orderNo, Integer status, String user_id, String course_id, String course_name, Double price) {
        System.out.println("订单编号 = " + orderNo);
        System.out.println("状态编码 = " + status);
        Integer integer = orderService.updateOrder(orderNo, status);
        System.out.println("订单更新 = " + integer);

        if (integer == 1) {
            // 创建支付订单信息
            PayOrder po = new PayOrder();
            po.setOrderNo(orderNo);// 订单号
            po.setUserId(user_id);// 购买者
            po.setProductId(course_id); //课程（产品）编号
            po.setProductName(course_name); //课程（产品）名称
            po.setAmount(price);// 金额
            po.setCount(1); // 购买数量
            po.setCurrency("cny"); //货币类型：人民币
            po.setChannel("weChat");  //支付渠道：weChat-微信支付
            po.setStatus(2); // 支付成功
            po.setOrderType(1);//类型 1-购买课程
            po.setSource(3); // 支付来源 1-app 2-h5 3-pc
            String ip = "192.168.1.1";
//            if (request != null) {
//                ip = request.getHeader("X-FORWARDED-FOR");
//                if (ip == null || "".equals(ip)) {
//                    ip = request.getRemoteAddr();
//                }
//            }
            po.setClientIp(ip); // 客户端ip
            po.setCreatedTime(new Date());
            po.setUpdatedTime(new Date());
            //支付订单信息
            orderService.saveOrderInfo(po);

            // 记录支付操作的日志
            PayOrderRecord por = new PayOrderRecord();
            por.setOrderNo(orderNo);// 订单号
            por.setType("PAY"); //操作类型：CREATE\|PAY\|REFUND...
            por.setPaidAmount(price);
            por.setFromStatus("1");
            por.setToStatus("2");
            por.setCreatedBy(user_id);
            por.setCreatedAt(new Date());
            System.out.println("创建订单记录 = " + orderNo);
            // 支付订单状态日志
            orderService.saveOrderRecord(por);
        }
        return integer;
    }

    @GetMapping("deleteOrder")
    public Integer deleteOrder(String orderno) {
        return orderService.deleteOrder(orderno);
    }

    @GetMapping("getOKOrderCourseIds")
    public List<Object> getOKOrderCourseIds(@RequestParam Integer userid) {
        System.out.println("获取" + userid + "的购买的课程编号！");
        List<UserCourseOrder> list = orderService.getOKOrderCourseIds(userid);
        List<Object> list2 = new ArrayList<>();
        for (UserCourseOrder order : list) {
            list2.add(order.getCourseId());
        }
        System.out.println("已购成功的课程id：" + list2);
        return list2;
    }
}
