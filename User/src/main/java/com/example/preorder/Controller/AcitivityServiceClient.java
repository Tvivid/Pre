package com.example.preorder.Controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Activity")
public interface AcitivityServiceClient {

    

}
