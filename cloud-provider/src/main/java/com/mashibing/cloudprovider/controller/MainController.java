package com.mashibing.cloudprovider.controller;

import com.mashibing.cloudprovider.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/getName")
    public String getName() {
        return "zhangxiang";
    }

    @Autowired
    HealthStatusService healthStatusService;

    @GetMapping("/healthStatus")
    public String setStatus(@RequestParam("status") Boolean status) {
        healthStatusService.setStatus(status);
        return "ok";
    }
}
