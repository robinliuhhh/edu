package com.robin.eduadboot.controller;

import com.robin.eduadboot.entity.PromotionAd;
import com.robin.eduadboot.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ad")
@CrossOrigin // 解决跨域
public class AdController {

    @Autowired
    private AdService adService;

    @GetMapping("getAdsBySpaceId/{spaceid}")
    public List<PromotionAd> getAdsBySpaceId(@PathVariable("spaceid") Integer sid){
        return adService.getAdsBySpaceId(sid);
    }
}
