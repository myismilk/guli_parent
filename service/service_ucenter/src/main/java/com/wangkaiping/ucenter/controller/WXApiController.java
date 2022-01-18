package com.wangkaiping.ucenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.wangkaiping.commonutils.utils.JwtUtils;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import com.wangkaiping.ucenter.config.WeiXin;
import com.wangkaiping.ucenter.entity.Member;
import com.wangkaiping.ucenter.service.MemberService;
import com.wangkaiping.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WXApiController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/callback")
    public String callback(String code,String statue){
        //1、获取code值，临时票据，类似于验证码


        //2、拿着code访问微信的一个固定地址，得到两个值 access_token和 openid
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessToken = String.format(baseAccessTokenUrl, WeiXin.APPID, WeiXin.APPSECRET, code);
        //请求地址获取放回的值，这里就需要用到一种技术Httpclient，模拟浏览器发送请求得到结果。
        try {
            String result = HttpClientUtils.get(accessToken);
            System.out.println(result);
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //根据openid判断数据库中是否已经存在这个用户。
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid",openid);
            Member member = memberService.getOne(queryWrapper);
            if (member == null){
                //用户不存在，需要进行注册
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String result2 = HttpClientUtils.get(userInfoUrl);
                System.out.println(result2);
                HashMap map2 = gson.fromJson(result2, HashMap.class);
                //获取扫码人的微信id，昵称，头像
                openid = (String) map2.get("openid");
                String nickname = (String) map2.get("nickname");
                String headimgurl = (String) map2.get("headimgurl");
                String sex = String.valueOf(map2.get("sex"));
                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                //向数据库添加
                memberService.save(member);
            }
            //将获取member的token返回给前端
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+token;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyExceptionOne(20001,"出现异常");
        }

    }


    @RequestMapping("/login")
    //获取微信登录二维码
    public String getWeChatLogin(){
        /*
        %s是占位符
        */
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl = "";
        try {
            redirectUrl = URLEncoder.encode(WeiXin.REDIRECTURl,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        /*通过这个方法将baseUrl的%s对应的替换掉*/
        String url = String.format(
                baseUrl,
                WeiXin.APPID,redirectUrl,"atguli");

        System.out.println(url);

        return "redirect:"+url;
    }
}
