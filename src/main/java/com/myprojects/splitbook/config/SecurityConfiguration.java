package com.myprojects.splitbook.config;

import com.myprojects.splitbook.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final LoginService loginService;

    public SecurityConfiguration(LoginService loginService) {
        this.loginService = loginService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/index","/signup","/signupprocess").permitAll()
                //.and().authorizeHttpRequests().requestMatchers("/admin").hasRole("ADMIN")
                .and().authorizeHttpRequests().requestMatchers("/user").hasRole("USER").anyRequest().authenticated()
                .and().userDetailsService(loginService)
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/welcome",true)
                .and().logout()
                .logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/login")
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();       //TODO Use a encrypting password encoder later
    }

}
