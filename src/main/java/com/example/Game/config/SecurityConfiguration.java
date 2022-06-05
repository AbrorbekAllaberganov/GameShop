package com.example.Game.config;

import com.example.Game.security.JwtAuthEntryPoint;
import com.example.Game.security.JwtConfigurer;
import com.example.Game.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    public SecurityConfiguration(@Lazy UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .headers().frameOptions().and().and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/swagger-ui.html","/webjars/**","/swagger-resources","/swagger-resources/**","/v2/**","/csrf").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable()
                .apply(new JwtConfigurer(jwtTokenProvider));
        ;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}
