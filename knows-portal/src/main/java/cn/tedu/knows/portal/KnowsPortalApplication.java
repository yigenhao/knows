package cn.tedu.knows.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author snake
 * @create 2021-09-16 21:00
 */
//SpringBoot主程序所在的包下的及其子包所有带注册bean功能的注解生效
@SpringBootApplication
/*
* @MapperScan:扫描指定包下的所有类和接口完成绑定
* 相当于给指定包下的所有类和接口类和接口添加了@Mapper注解，即在Mybatis-plus中注册Mapper
* */
@MapperScan("cn.tedu.knows.portal.mapper")
public class KnowsPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowsPortalApplication.class,args);

    }
}
