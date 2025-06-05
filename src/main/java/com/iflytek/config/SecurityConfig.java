package com.iflytek.config;


import com.iflytek.filter.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // 禁用 csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/user/register",
                        "/user/loginByFace"
                ).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/user/login").permitAll() // 允许登录接口匿名访问
                .anyRequest().authenticated(); // 其他接口需要认证

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
