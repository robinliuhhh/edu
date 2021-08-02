package com.robin.eduorderboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.eduorderboot.entity.UserCourseOrder;
import org.springframework.stereotype.Component;

@Component
public interface OrderDao extends BaseMapper<UserCourseOrder> {
}
