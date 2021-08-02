package com.robin.eduadboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.eduadboot.entity.PromotionAd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdDao extends BaseMapper<PromotionAd> {

    //List<PromotionAd> getAdsBySpaceId(@Param("space_id") Integer sid);
}
