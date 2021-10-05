package cn.tedu.knows.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//EnableDiscoveryClient表示当前项目是可以呗注册中心发现的客户端
//也就是整体项目中的一个为服务项目或组成部分
//最重要的就是他会注册到nacos
@EnableDiscoveryClient
public class KnowsResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsResourceApplication.class, args);
    }

}
