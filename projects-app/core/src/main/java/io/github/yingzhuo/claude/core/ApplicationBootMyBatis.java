package io.github.yingzhuo.claude.core;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(basePackages = "io.github.yingzhuo.claude", annotationClass = Mapper.class)
@EnableTransactionManagement
@Configuration
public class ApplicationBootMyBatis {
}
