package com.robin.eduorderboot.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

@Data
@Table(name = "pay_order_record") //支付订单状态日志表
public class PayOrderRecord implements Serializable {
    private static final long serialVersionUID = -27661694260575560L;
    /**
     * ID
     */
    @Id
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 操作类型：CREATE|PAY|REFUND...
     */
    private String type;
    /**
     * 原订单状态
     */
    private String fromStatus;
    /**
     * 新订单状态
     */
    private String toStatus;
    /**
     * 实付金额，单位为分
     */
    private Double paidAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作人
     */
    private String createdBy;
    /**
     * 操作时间
     */
    private Date createdAt;

}
