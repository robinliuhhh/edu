package com.robin.eduorderboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.robin.eduorderboot.entity.PayOrder;
import com.robin.eduorderboot.entity.PayOrderRecord;
import com.robin.eduorderboot.entity.UserCourseOrder;
import com.robin.eduorderboot.mapper.OrderDao;
import com.robin.eduorderboot.mapper.PayOrderDao;
import com.robin.eduorderboot.mapper.PayOrderRecordDao;
import com.robin.eduorderboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PayOrderDao payOrderDao;

    @Autowired
    private PayOrderRecordDao payOrderRecordDao;

    // 保存订单
    public void saveOrder(String orderNo, String user_id, String course_id, String activity_course_id, String source_type) {
        UserCourseOrder order = new UserCourseOrder();

        order.setOrderNo(orderNo);
        order.setUserId(user_id);
        order.setCourseId(course_id);
        order.setActivityCourseId(Integer.parseInt(activity_course_id));
        order.setSourceType(source_type);
        order.setIsDel(0);
        order.setStatus(0);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        orderDao.insert(order);
    }

    // 更新订单状态
    public Integer updateOrder(String orderNo, int status) {
        UserCourseOrder order = new UserCourseOrder();
        order.setStatus(status);

        QueryWrapper<UserCourseOrder> qw = new QueryWrapper();
        qw.eq("order_no", orderNo);
        return orderDao.update(order, qw);
    }

    // 删除订单
    public Integer deleteOrder(String orderNo) {
        QueryWrapper<UserCourseOrder> qw = new QueryWrapper();
        qw.eq("order_no", orderNo);
        // delete from user_course_order where order_no = orderNo
        return orderDao.delete(qw);
    }

    // 查询某个用户的全部订单
    @Override
    public List<UserCourseOrder> getOKOrderCourseIds(Integer userId) {
        QueryWrapper<UserCourseOrder> qw = new QueryWrapper();
        qw.select("course_id"); // SELECT course_id FROM edu_order.user_course_order
        qw.eq("STATUS", 20); //WHERE STATUS = 20
        qw.eq("is_del", 0); //AND is_del = 0
        qw.eq("user_id", userId); //AND  user_id = #{userId
        return orderDao.selectList(qw);
    }

    @Override
    public void saveOrderInfo(PayOrder payOrder) {
        payOrderDao.insert(payOrder);
    }

    @Override
    public void saveOrderRecord(PayOrderRecord payOrderRecord) {
        payOrderRecordDao.insert(payOrderRecord);
    }
}
