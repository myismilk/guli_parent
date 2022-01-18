package com.wangkaiping.order.clientInterface;

import com.wangkaiping.commonutils.EntityVo.MemberVoY;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {
    @GetMapping("/ucenter/member/getUserInfoByIdY/{id}")
    public MemberVoY getUserInfoByIdY(@PathVariable("id") String id);
}
