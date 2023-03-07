package com.tandaima.tool.core;

import com.tandaima.tool.config.constants.ToolConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zbrcel@gmail.com
 * @description
 */
@Configuration
public class PropToolService {

    /**
     * 是否测试模式(测试、生产)
     */
    public static String PROFILES ;

    /**
     * 判断是否是测试环境
     */
    public static Boolean isTest(){
        return PropToolService.PROFILES.equals(ToolConstants.PROFILES_TEST);
    }

    /**
     * 判断不是正式环境
     */
    public static Boolean isNotProd(){
        return !PropToolService.PROFILES.equals(ToolConstants.PROFILES_PROD);
    }

    /**
     * 判断是否是正式环境
     */
    public static Boolean isProd(){
        return PropToolService.PROFILES.equals(ToolConstants.PROFILES_PROD);
    }

    /**
     * 判断是否是开发环境
     */
    public static Boolean isDev(){
        return PropToolService.PROFILES.equals(ToolConstants.PROFILES_DEV);
    }

    @Value("${spring.profiles.active}")
    public void setProfile(String profile) {
        PropToolService.PROFILES = profile;
    }
}
