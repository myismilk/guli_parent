package com.wangkaiping.order.service.impl;

import com.wangkaiping.commonutils.EntityVo.FrontCourseInfoVo;
import com.wangkaiping.commonutils.EntityVo.MemberVoY;
import com.wangkaiping.order.clientInterface.EduServiceClient;
import com.wangkaiping.order.clientInterface.UcenterClient;
import com.wangkaiping.order.entity.Order;
import com.wangkaiping.order.mapper.OrderMapper;
import com.wangkaiping.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkaiping.order.utils.OrderNoUtil;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduServiceClient eduServiceClient;

    @Autowired
    private UcenterClient ucenterClient;

    //生成订单的方法
    @Override
    public String createOrderByCourseIdAndUserId(String courseId, String id) {
        //生成一个订单需要用到课程的相关信息，还有用户的相关信息，所以我们这里要远程调用
        System.out.println("进来了");

        System.out.println("========================================");
        //远程调用获取课程信息
        FrontCourseInfoVo courseInfoY = eduServiceClient.getCourseInfoY(courseId);
        System.out.println(courseInfoY);

        //远程调用获取用户信息
        MemberVoY memberVoY = ucenterClient.getUserInfoByIdY(id);
        System.out.println(memberVoY);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseInfoY.getId());
        order.setCourseTitle(courseInfoY.getTitle());
        order.setCourseCover(courseInfoY.getCover());
        order.setTeacherName(courseInfoY.getTeacherName());
        order.setMemberId(memberVoY.getId());
        order.setNickname(memberVoY.getNickname());
        order.setMobile(memberVoY.getMobile());
        order.setTotalFee(courseInfoY.getPrice());
        order.setPayType(1);
        order.setStatus(0);
        System.out.println(order);

        //添加订单
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
