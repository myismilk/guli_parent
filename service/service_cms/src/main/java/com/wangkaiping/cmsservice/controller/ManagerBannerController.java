package com.wangkaiping.cmsservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.cmsservice.entity.CrmBanner;
import com.wangkaiping.cmsservice.entity.vo.BannerVo;
import com.wangkaiping.cmsservice.service.CrmBannerService;
import com.wangkaiping.commonutils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cmsservice/mbanner")
@CrossOrigin
public class ManagerBannerController {
    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询查询banner
    @GetMapping("/findBannerByPage/{current}/{limit}")
    public R findBannerByPage(@PathVariable("current") long current, @PathVariable("limit") long limit){
        Page<CrmBanner> crmBannerPage = new Page<>(current,limit);
        crmBannerService.page(crmBannerPage,null);
        List<CrmBanner> crmBannersPageRecords = crmBannerPage.getRecords();
        long total = crmBannerPage.getTotal();
        return R.ok().data("total",total).data("crmBannersPageRecords",crmBannersPageRecords);

    }

    //条件分页查询查询banner
    @PostMapping("/findBannerByPageAndWhere/{current}/{limit}")
    public R findBannerByPageAndWhere(@PathVariable("current") long current,@PathVariable("limit") long limit,@RequestBody(required = false) BannerVo bannerVo){
        String title = bannerVo.getTitle();
        String begin = bannerVo.getBegin();
        String end = bannerVo.getEnd();
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        Page<CrmBanner> crmBannerPage = new Page<>(current,limit);
        crmBannerService.page(crmBannerPage,queryWrapper);
        List<CrmBanner> crmBannersPageRecords = crmBannerPage.getRecords();
        long total = crmBannerPage.getTotal();
        return R.ok().data("total",total).data("crmBannersPageRecords",crmBannersPageRecords);
    }

    //根据id查询Banner
    @GetMapping("/findBannerById/{bannerId}")
    public R findBannerById(@PathVariable("bannerId") String bannerId){
        CrmBanner crmBanner = crmBannerService.getById(bannerId);
        System.out.println(crmBanner);
        return R.ok().data("crmBanner",crmBanner);
    }

    //添加Banner的方法
    @PostMapping("/saveBanner")
    public R saveBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //修改Banner的方法
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    //根据id删除Banner的方法
    @DeleteMapping("/deleteBannerById/{bannerId}")
    public R deleteBannerById(@PathVariable("bannerId") String bannerId){
        crmBannerService.removeById(bannerId);
        return R.ok();
    }
}
