package com.myproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecuritySettings extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// auth
		// 	.inMemoryAuthentication()
	 //                .withUser("user").password("password").roles("ADMIN").and()
	 //                .withUser("admin").password("password").roles("EDITOR", "ADMIN");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.and()
				.csrf()
				.disable()
				.formLogin()
				.loginProcessingUrl("/static/j_spring_security_check")
				.loginPage("/login")
				.failureUrl("/login?login_error")
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.defaultSuccessUrl("/")
			.and()
				.logout()
				.logoutUrl("/j_spring_security_logout")
				.logoutSuccessUrl("/login?logout");
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}


}