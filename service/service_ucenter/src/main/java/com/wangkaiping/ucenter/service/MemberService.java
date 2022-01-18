package com.wangkaiping.ucenter.service;

import com.wangkaiping.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-13
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(Member member);
}
