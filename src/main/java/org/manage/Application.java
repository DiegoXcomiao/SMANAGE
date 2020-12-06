package org.manage;

import org.manage.common.redis.Redis;
import org.manage.config.RedisConfig;
import org.manage.controller.RoleRedisController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:tomcat.properties"})
@PropertySource({"classpath:datasource.properties"})
@PropertySource({"classpath:pagehelper.properties"})
@PropertySource({"classpath:picture.properties"})

@ComponentScan(value = "org.manage", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = {RedisConfig.class, RoleRedisController.class, Redis.class}))

public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
