package com.pzl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //访问数据库权限(加密)
    @Autowired
    private SpringSecurityUserService springSecurityUserService;
    //密码加密方式
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(springSecurityUserService).passwordEncoder(passwordEncoder());
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置在页面可以通过iframe访问受保护的页面，默认为不允许访问
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                //指定哪些资源不需要进行权限校验，可以使用通配符
                .antMatchers( "/static/**","/css/**","/img/**","/plugins/**","/js/**","/login.html").permitAll()
                ///pages/**路径下资源，已经经过认证（不是匿名用户）可以访问
                .antMatchers("/pages/**").authenticated()
                //前面没有匹配上的请求，全部需要认证；
                .anyRequest().authenticated()
                .and()

                //指定登录界面，并且设置为所有人都能访问；
                .formLogin()
                .loginPage("/login.html")
                //登陆请求处理接口
                .loginProcessingUrl("/login")
                //用户名文本框 name属性
                .usernameParameter("username")
                //密码文本框 name属性
                .passwordParameter("password")
                //如果登录成功会跳转到哪里
                .defaultSuccessUrl("/pages/main.html",true)
                //如果登录失败会跳转到哪里
                .failureForwardUrl("/login.html")
                .permitAll()
                .and()

                //登出配置
                .logout()
                .logoutUrl("/logout") //指定登出的地址，默认是"/logout"
                .logoutSuccessUrl("/login.html")   //登出后的跳转地址
                .clearAuthentication(true)  //清楚身份信息
                .invalidateHttpSession(true)  //session 失效，默认为true
                .deleteCookies("usernameCookie","urlCookie") //在登出同时清除cookies

                .and()
//               csrf：对应CsrfFilter过滤器
//               disabled：是否启用CsrfFilter过滤器，如果使用自定义登录页面需要关闭此项，
//			     否则登录操作会被禁用（403）
                .csrf()
                .disable();
    }
}
