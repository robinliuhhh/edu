package com.robin.eduadboot.service;

import com.robin.eduadboot.entity.PromotionAd;

import java.util.List;

public interface AdService {

    List<PromotionAd> getAdsBySpaceId(Integer sid);

}
