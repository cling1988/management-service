package com.app.config;

import com.app.common.CommonKey;
import com.app.service.impl.DBUserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration
        extends WebSecurityConfigurerAdapter {
    private static final Logger log = LogManager.getLogger(BasicAuthConfiguration.class);

//    @Autowired
//    DataSource dataSource;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(dbUserDetails()).passwordEncoder(bCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(CommonKey.URL_AUTHORIZATION+"/**").permitAll()
                .antMatchers(CommonKey.URL_PRODUCT_VIEW +"/**").hasAnyAuthority(CommonKey.PERMISSION_PRODUCT_VIEW)
                .antMatchers(CommonKey.URL_PRODUCT_EDIT +"/**").hasAnyAuthority(CommonKey.PERMISSION_PRODUCT_EDIT)
                .antMatchers(CommonKey.URL_USER_VIEW +"/**").hasAnyAuthority(CommonKey.PERMISSION_USER_VIEW)
                .antMatchers(CommonKey.URL_USER_EDIT +"/**").hasAnyAuthority(CommonKey.PERMISSION_USER_EDIT)
                .antMatchers(CommonKey.URL_OUTLET_VIEW +"/**").hasAnyAuthority(CommonKey.PERMISSION_OUTLET_VIEW)
                .antMatchers(CommonKey.URL_OUTLET_EDIT +"/**").hasAnyAuthority(CommonKey.PERMISSION_OUTLET_EDIT)
                .antMatchers(CommonKey.URL_PRODUCTION_VIEW +"/**").hasAnyAuthority(CommonKey.PERMISSION_PRODUCTION_VIEW)
                .antMatchers(CommonKey.URL_PRODUCTION_EDIT +"/**").hasAnyAuthority(CommonKey.PERMISSION_PRODUCTION_EDIT)
                .antMatchers(CommonKey.URL_EVENT_VIEW +"/**").hasAnyAuthority(CommonKey.PERMISSION_EVENT_VIEW)
                .antMatchers(CommonKey.URL_EVENT_EDIT +"/**").hasAnyAuthority(CommonKey.PERMISSION_EVENT_EDIT)
                .antMatchers(CommonKey.URL_PLANNING_VIEW +"/**").hasAnyAuthority(CommonKey.PERMISSION_PLANNING_VIEW)
                .antMatchers(CommonKey.URL_PLANNING_EDIT +"/**").hasAnyAuthority(CommonKey.PERMISSION_PLANNING_EDIT)
                .antMatchers(CommonKey.URL_REPORT +"/**").hasAnyAuthority(CommonKey.PERMISSION_REPORT)
//                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(unauthorizedEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors()
                .and().addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService dbUserDetails() {
        return new DBUserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        (request.getAttribute(CommonKey.EXCEPTION_MESSAGE) != null ?
                                request.getAttribute(CommonKey.EXCEPTION_MESSAGE).toString() :
                                authException.getMessage()));
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN,
                accessDeniedException.getMessage());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        return objectMapper;
    }
}