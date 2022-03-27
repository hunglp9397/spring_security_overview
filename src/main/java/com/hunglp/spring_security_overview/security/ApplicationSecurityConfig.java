package com.hunglp.spring_security_overview.security;

import com.hunglp.spring_security_overview.student.Student;
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
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINISTRATOR.name())
                .anyRequest()
                .authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
//        UserDetails hunglp = User.builder()
//                .username("hunglp")
//                .password(passwordEncoder.encode("hunglp9397"))
//                .roles(ApplicationUserRole.STUDENT.name()).build();
//
//        UserDetails huyenbear = User.builder()
//                .username("huyenbear")
//                .password(passwordEncoder.encode("huyenbear123"))
//                .roles(ApplicationUserRole.ADMIN.name()).build();
//
//        UserDetails administrator = User.builder()
//                .username("administrator")
//                .password(passwordEncoder.encode("administrator"))
//                .roles(ApplicationUserRole.ADMIN.name()).build();

        UserDetails hunglp = User.builder()
                .username("hunglp")
                .password(passwordEncoder.encode("hunglp"))
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthories())
                .build();

        UserDetails huyenbear = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthories())
                .build();

        UserDetails administrator = User.builder()
                .username("administrator")
                .password(passwordEncoder.encode("administrator"))
                .authorities(ApplicationUserRole.ADMINISTRATOR.getGrantedAuthories())
                .build();



        return new InMemoryUserDetailsManager(hunglp, huyenbear,administrator);
    }
}
