/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bruno.biblioteca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                //.requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(
                        "/**",
                        "/js/**",
                        "/css/**",
                        "/img/**").permitAll()
                .anyRequest().authenticated());

        http.formLogin(formLogin
                -> formLogin
//                        .loginPage("/login")
//                        .loginProcessingUrl("/logincheck")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/inicio")
                        .permitAll()
        );
        http.logout(logout
                -> logout
                        //.logoutUrl("/logout")
                        //.logoutSuccessUrl("/login")
                        .permitAll()
        );
//http.httpBasic(withDefaults());
//http.csrf(csrf -> csrf.disable());

        return http.build();
    }

}
