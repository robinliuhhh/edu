package com.robin.educourseboot.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "edu-order-boot", path = "order") // 去eureka注册中心找服务
public interface OrderRemoteService {

    @GetMapping("getOKOrderCourseIds")
    List<Object> getOKOrderCourseIds(@RequestParam(value = "userid") Integer userid);
}

