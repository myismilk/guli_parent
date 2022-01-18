package com.wangkaiping.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.commonutils.utils.JwtUtils;
import com.wangkaiping.commonutils.utils.MD5;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import com.wangkaiping.ucenter.entity.Member;
import com.wangkaiping.ucenter.mapper.MemberMapper;
import com.wangkaiping.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-13
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    //登录
    @Override
    public String login(Member member) {
        String id = member.getId();
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyExceptionOne(20001,"账号和密码不能为空");
        }

        //查询改手机号用户是否存在
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile",mobile);
        Member mobileMember = baseMapper.selectOne(queryWrapper);
        if(mobileMember == null){
            throw new MyExceptionOne(20001,"用户不存在");
        }
        password = MD5.encrypt(password);
        if(!password.equals(mobileMember.getPassword())){
            throw new MyExceptionOne(20001,"密码错误");
        }

        if(mobileMember.getIsDisabled()){
            throw new MyExceptionOne(20001,"用户已经没禁用");
        }

        //登录成功
        String token = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getPassword());
        return token;
    }

    @Override
    public void register(Member member) {
        if(StringUtils.isEmpty(member.getMobile()) || StringUtils.isEmpty(member.getPassword())){
            throw new MyExceptionOne(20001,"账号和密码不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile",member.getMobile());
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new MyExceptionOne(20001,"账号已经存在");
        }
        baseMapper.insert(member);
    }
}
