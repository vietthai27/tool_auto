package com.fis.tool.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fis.tool.JWT.JwtAuthenProvider;
import com.fis.tool.JWT.JwtTokenFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenFilter tokenFilter;

	@Autowired
	JwtAuthenProvider authentication;

	@Bean
	public AuthenticationManager getauthManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authentication);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().disable();

		http.authorizeHttpRequests().antMatchers("/test-api/user-page/**").hasAnyAuthority("USER", "ADMIN")
				.antMatchers("/test-api/admin-page/**").hasAuthority("ADMIN").antMatchers("/login").permitAll()
				.antMatchers("/resetPassRequest").permitAll().antMatchers("/resetPassValidate").permitAll()
				.antMatchers("/test/**").permitAll().antMatchers("/signupRequest").permitAll()
				.antMatchers("/signupValidate").permitAll().antMatchers("/getUserData/**").permitAll()
				.antMatchers("/fis-user/**").permitAll().anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http
				// ...
				.headers()
				.contentSecurityPolicy(
						"script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
				.reportOnly();
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
