package com.wangkaiping.cmsservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.cmsservice.entity.CrmBanner;
import com.wangkaiping.cmsservice.entity.vo.BannerVo;
import com.wangkaiping.cmsservice.service.CrmBannerService;
import com.wangkaiping.commonutils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/cmsservice/abanner")
@CrossOrigin
public class AdminBannerController {
    @Autowired
    private CrmBannerService crmBannerService;


    //增加banner
    @PostMapping("/saveBanner")
    public R saveBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //根据bannerId删除banner
    @DeleteMapping("/deleteBannerById/{bannerId}")
    public R deleteBannerById(@PathVariable("bannerId") String bannerId){
        crmBannerService.removeById(bannerId);
        return R.ok();
    }

    //修改banner
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();

    }

    //分页查询查全部
    @GetMapping("/findAllBannerByPage/{current}/{limit}")
    public R findAllBannerByPage(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit){
        Page<CrmBanner> bannerPage = new Page<>(current,limit);
        crmBannerService.page(bannerPage,null);
        long total = bannerPage.getTotal();
        List<CrmBanner> pageRecords = bannerPage.getRecords();
        return R.ok().data("total",total).data("pageRecords",pageRecords);
    }

    //获得所有的banner
    @Cacheable(value = "banner",key = "'bannerList'")
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        System.out.println("来到了getAllBanner");
        List<CrmBanner> bannerList = crmBannerService.list(null);
        return R.ok().data("bannerList",bannerList);
    }

    //条件分页查询查
    @GetMapping("/findAllBannerByPageAndWhere/{current}/{limit}")
    public R findAllBannerByPageAndWhere(@PathVariable("current") long current,
                                         @PathVariable("limit") long limit,
                                         @RequestBody(required = false)BannerVo bannerVo){
        Page<CrmBanner> bannerPage = new Page<>(current,limit);
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        String title = bannerVo.getTitle();
        String begin = bannerVo.getBegin();
        String end = bannerVo.getEnd();
        if(!StringUtils.isEmpty(title)){
            crmBannerQueryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)){
            crmBannerQueryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            crmBannerQueryWrapper.le("gmt_create",end);
        }
        crmBannerService.page(bannerPage,crmBannerQueryWrapper);
        long total = bannerPage.getTotal();
        List<CrmBanner> pageRecords = bannerPage.getRecords();
        return R.ok().data("total",total).data("pageRecords",pageRecords);
    }

}

