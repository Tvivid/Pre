package com.example.preorder.Feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user", url = "http://localhost:8083/v1/internal")
public interface ActivityFeignClient {
}
