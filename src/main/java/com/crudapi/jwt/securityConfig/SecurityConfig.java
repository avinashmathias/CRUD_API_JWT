package com.crudapi.jwt.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
         	.csrf().disable()
         	.authorizeRequests().anyRequest().authenticated()
         .and()
         	.httpBasic()
         .and()
         .formLogin()
         	.loginPage("/showMyLoginPage")
         	.loginProcessingUrl("/authenticateTheUser")
         	.permitAll()
         .and()
	        .logout()  
	        .permitAll();

    }
  
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}password")
            .roles("USER");
    }

}
