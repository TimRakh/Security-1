package com.timurka.firstSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Stanislav Rakitov
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("anna")
                .password(encoder().encode("anna"))
                .roles("READ", "WRITE");
        auth.inMemoryAuthentication()
                .withUser("tomas")
                .password(encoder().encode("tomas"))
                .roles("READ");
        auth.inMemoryAuthentication()
                .withUser("olga")
                .password(encoder().encode("olga"))
                .roles("DELETE");
        auth.inMemoryAuthentication()
                .withUser("timur")
                .password(encoder().encode("timur"))
                .authorities("read");

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin().and()
                .authorizeRequests().antMatchers("/persons").permitAll()
                .and()
                .authorizeRequests().antMatchers("/persons/create").hasRole("WRITE")
                .and()
                .authorizeRequests().antMatchers("/persons/delete").hasAnyRole("DELETE","WRITE")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }
}