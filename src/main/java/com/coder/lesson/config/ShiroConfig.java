package com.coder.lesson.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.coder.lesson.shiro.CustomAccessControllerFilter;
import com.coder.lesson.shiro.CustomHashedCredentialsMatcher;
import com.coder.lesson.shiro.CustomRealm;
import com.coder.lesson.shiro.RedisCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @类名 ShiroConfig
 * @描述 shiro配置类
 * @创建人 张全蛋
 * @创建日期 2020/2/19 16:57
 * @版本 1.0
 **/
@Configuration
public class ShiroConfig {

    /**
     * redis缓存管理
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    /**
     * 密码校验器
     *
     * @return
     */
    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher() {
        return new CustomHashedCredentialsMatcher();
    }

    /**
     * 注入自定义realm
     *
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm realm = new CustomRealm();
        realm.setCredentialsMatcher(customHashedCredentialsMatcher());
        realm.setCacheManager(redisCacheManager());
        return realm;
    }

    /**
     * 配置安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 请求过滤
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> filterLinkedHashMap = new LinkedHashMap<>();
        filterLinkedHashMap.put("token", new CustomAccessControllerFilter());
        shiroFilterFactoryBean.setFilters(filterLinkedHashMap);

        Map<String, String> map = new LinkedHashMap<>();
        // 不拦截以下请求
        map.put("/api/user/login", "anon");
        map.put("/api/user/token", "anon");
        map.put("/view/**", "anon");
        map.put("/druid/**", "anon");
        map.put("/swagger/**", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/favicon.ico", "anon");
        map.put("/captcha.jpg", "anon");
        map.put("/", "anon");
        map.put("/csrf", "anon");
        map.put("/images/**", "anon");
        map.put("/js/**", "anon");
        map.put("/layui/**", "anon");
        map.put("/css/**", "anon");
        map.put("/treetable-lay/**", "anon");
        // 对所有的请求进行token和认证校验
        map.put("/**", "token,authc");

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/api/user/unLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @throws    
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * thymeleaf使用shiro标签需要添加此支持
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
