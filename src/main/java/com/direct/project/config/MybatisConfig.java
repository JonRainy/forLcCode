package com.direct.project.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = { "com.direct.project"}, annotationClass = Repository.class, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {



    @Autowired
    private MybatisProperties properties;

//    @Bean
//    @Primary
//    public DataSource createDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource dataSource) {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setMapperLocations();
//        return sessionFactoryBean;
//    }

    @Bean
    public PerformanceMonitorInterceptor performanceInterceptor() {
        return new PerformanceMonitorInterceptor();
    }
}
