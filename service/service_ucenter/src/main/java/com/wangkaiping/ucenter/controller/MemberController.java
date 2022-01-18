package com.wangkaiping.ucenter.controller;


import com.wangkaiping.commonutils.EntityVo.MemberVoY;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.commonutils.utils.JwtUtils;
import com.wangkaiping.ucenter.entity.Member;
import com.wangkaiping.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-13
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody Member member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }


    //注册
    @PostMapping("/register")
    public R register(@RequestBody Member member){
        memberService.register(member);
        return R.ok();
    }
    
    //根据token获取用户信息
    @GetMapping("/getUserByToken")
    public R getUserByToken(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(id);
        System.out.println(member);
        return R.ok().data("member",member);
    }

    //被远程调用根据用户id获取用户的详情信息
    @GetMapping("/getUserInfoByIdY/{id}")
    public MemberVoY getUserInfoByIdY(@PathVariable("id") String id){
        Member member = memberService.getById(id);
        MemberVoY memberVoY = new MemberVoY();
        BeanUtils.copyProperties(member,memberVoY);
        return memberVoY;
    }


}

