package com.mashibing.cloudconsumer.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 现在，我们想要做的是通过eureka去调用服务提供方的getName方法
 * 首先来一个eurekaClient对象
 */
@RestController
public class MainController {

    @Autowired
    EurekaClient client;

    @Autowired
    DiscoveryClient client2;

    @GetMapping("/getApp")
    public String getApp() {
        return "小红薯";
    }

    @GetMapping("/getService")
    public Object getService() {
        List<String> services = client2.getServices();
        for (String service : services) {
            System.out.println(service);
        }
        return "xxoo";
    }

    @GetMapping("getInstance")
    public Object getInstance() {
        return client2.getInstances("provider");
    }

    @GetMapping("getProviderMethod")
    public Object getProviderMethod() {
        List<InstanceInfo> providers = client.getInstancesByVipAddress("provider", false);
        if (providers.size() > 0) {
            InstanceInfo instanceInfo = providers.get(0);
            if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {
                String hostName = instanceInfo.getHostName();
                int port = instanceInfo.getPort();
                String url = "http://"+hostName+":"+port+"/getName";
                System.out.println(url);
                RestTemplate restTemplate = new RestTemplate();
                String resp = restTemplate.getForObject(url, String.class);
                System.out.println("返回消息是:"+resp);
            }
        }
        return "xx";
    }
}
