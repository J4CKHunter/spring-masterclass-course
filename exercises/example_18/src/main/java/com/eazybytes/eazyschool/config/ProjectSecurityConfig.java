package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        //formLogin() -> http ile username password alınırken
        //httpBasic() -> rest api ile username password alınırken

        // web app'in içindeki tüm requestlere izin verir
//        http.authorizeRequests().anyRequest().permitAll()
//                .and().formLogin()
//                .and().httpBasic();

        // web app'in içindeki tüm requestleri yasaklar
//        http.authorizeRequests().anyRequest().denyAll()
//                .and().formLogin()
//                .and().httpBasic();

        //web app'in içindeki bizim söylediğimiz page'lere / API'lere  bizim söylediğimiz izinleri verir
//        http.authorizeRequests()
//                .mvcMatchers("/home", "", "/").permitAll()
//                .mvcMatchers("/holidays/**").permitAll()
//                .mvcMatchers("/contact").permitAll()
//                .mvcMatchers("/saveMsg").permitAll()
//                .mvcMatchers("/courses").authenticated()  // şifre ister
//                .mvcMatchers("/about").denyAll()          // hepsini reddeder
//                .and().formLogin()
//                .and().httpBasic();

//        http.csrf().disable()
        http.csrf().ignoringAntMatchers("/saveMsg")
//                .ignoringAntMatchers("/h2-console/**")
                .ignoringAntMatchers("/public/**")
                .ignoringAntMatchers("/api/**")
                .ignoringAntMatchers("/data-api/**")
                .ignoringAntMatchers("/school/actuator/**")
                .and().authorizeRequests()
                .mvcMatchers("/home", "", "/").permitAll()
                .mvcMatchers("/holidays/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/contact").permitAll()
                .mvcMatchers(HttpMethod.POST,"/contact").denyAll()
                .mvcMatchers("/saveMsg").permitAll()
                .mvcMatchers("/courses").authenticated()  // şifre ister
                .mvcMatchers("/about").denyAll()// hepsini reddeder
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/displayMessages").hasRole("ADMIN")
                .mvcMatchers("/public/**").permitAll()
                .mvcMatchers("/displayProfile").authenticated()
                .mvcMatchers("/updateProfile").authenticated()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/student/**").hasRole("STUDENT")
                .mvcMatchers("/api/**").authenticated()
                .mvcMatchers("/data-api/**").authenticated()
                .mvcMatchers("/school/actuator/**").hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureForwardUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
//                .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().httpBasic();

//        http.headers().frameOptions().disable();

        return http.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN", "USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
