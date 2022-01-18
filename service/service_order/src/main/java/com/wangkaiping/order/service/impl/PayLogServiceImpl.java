package com.wangkaiping.order.service.impl;

import com.wangkaiping.order.entity.PayLog;
import com.wangkaiping.order.mapper.PayLogMapper;
import com.wangkaiping.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-18
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
