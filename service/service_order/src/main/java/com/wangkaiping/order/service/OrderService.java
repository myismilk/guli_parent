package com.wangkaiping.order.service;

import com.wangkaiping.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-18
 */
public interface OrderService extends IService<Order> {

    String createOrderByCourseIdAndUserId(String courseId, String id);
}
