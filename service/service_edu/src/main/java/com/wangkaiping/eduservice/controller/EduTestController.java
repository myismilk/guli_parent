package com.wangkaiping.eduservice.controller;

import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.client.VdoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eduservice/test/user")
@CrossOrigin
public class EduTestController {

    @PostMapping("login")
    public R logintest(){
        return R.ok().data("token","admin");
    }
    @GetMapping("info")
    public R infotest(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://laowangedu.oss-cn-shenzhen.aliyuncs.com/2022/01/07/f89980639e9e4ff591985b669353c99efile.png");
    }
}
