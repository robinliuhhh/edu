package com.robin.eduadboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.robin.eduadboot.entity.PromotionAd;
import com.robin.eduadboot.mapper.AdDao;
import com.robin.eduadboot.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdDao adDao;

    @Override
    public List<PromotionAd> getAdsBySpaceId(Integer sid) {
        QueryWrapper<PromotionAd> qw = new QueryWrapper<>(); // select * from table
        qw.eq("space_id", sid); // where space_id = sid
        return adDao.selectList(qw);
    }
}
