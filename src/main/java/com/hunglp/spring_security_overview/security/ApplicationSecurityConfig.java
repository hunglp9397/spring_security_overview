package com.hunglp.spring_security_overview.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public  ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
                .anyRequest()
                .authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails hunglp = User.builder()
                .username("hunglp")
                .password(passwordEncoder.encode("hunglp9397"))
                .roles(ApplicationUserRole.STUDENT.name()).build();

        UserDetails huyenbear = User.builder()
                .username("huyenbear")
                .password(passwordEncoder.encode("huyenbear123"))
                .roles(ApplicationUserRole.ADMIN.name()).build();

        UserDetails administrator = User.builder()
                .username("administrator")
                .password(passwordEncoder.encode("administrator"))
                .roles(ApplicationUserRole.ADMIN.name()).build();

        return new InMemoryUserDetailsManager(hunglp, huyenbear,administrator);
    }
}
