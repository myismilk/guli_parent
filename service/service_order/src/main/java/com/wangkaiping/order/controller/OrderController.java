package com.wangkaiping.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.commonutils.utils.JwtUtils;
import com.wangkaiping.order.entity.Order;
import com.wangkaiping.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-18
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        System.out.println("进到了控制层");
        String id = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrderByCourseIdAndUserId(courseId,id);
        System.out.println(orderNo);
        return R.ok().data("orderNo",orderNo);
    }

    //根据订单编号查询订单信息
    @GetMapping("/getOrderInfoByOrderNo/{orderNo}")
    public R getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("order",order);
    }

}

