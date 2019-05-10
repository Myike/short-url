package cn.com.yunyoutianxia.tour.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: zhiwutu
 * @Date: 2019/5/10 15:33
 * @Description:
 */
//@Configuration
//@ComponentScan(basePackageClasses = CorsConfigurerAdapter.class)
//@EnableWebMvc
//@ComponentScan
public class CorsConfigurerAdapter extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        super.addCorsMappings(registry);
        registry.addMapping("/**").allowedOrigins("*");
//        super.addCorsMappings(registry);
    }
}
