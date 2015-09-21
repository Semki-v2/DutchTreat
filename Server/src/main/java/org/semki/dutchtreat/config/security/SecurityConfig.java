package org.semki.dutchtreat.config.security;

import org.semki.dutchtreat.mvc.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthSuccess authSuccessHandler;
	
	@Autowired
	private AuthFailure authFailureHandler;
	
	@Autowired
	private EntryPointUnauthorizedHandler entryPointUnauthrizedHandler;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private ReflectionSaltSource saltSource;
	
	@Autowired
	private Md5PasswordEncoder passEnc;

	@Autowired
	private DaoAuthenticationProvider authProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(entryPointUnauthrizedHandler)
						.and()
						.formLogin().loginPage("/app/auth/login").successHandler(authSuccessHandler).failureHandler(authFailureHandler)
						.and()
						.authorizeRequests()
						.antMatchers("/app/index.html",
									 "/app/events/**",
									 "/app/auth/login",
									 "/app/auth/registration",
									 "/app/bower_components/**",
									 "/app/components/**",
									 "/app/views/**",
									 "/app/styles/**",
									 "/app/app.js",
									 "/app/app.css",
									 "/app/favicon.ico").permitAll()
						.anyRequest().authenticated();
	}
	
	@Autowired
	public void configurationBuilder(AuthenticationManagerBuilder builder) throws Exception
	{		
		builder.authenticationProvider(authProvider);
	}
	
	
	@Bean
	public ReflectionSaltSource saltSource()
	{
		ReflectionSaltSource source = new ReflectionSaltSource();
		source.setUserPropertyToUse("email");
		
		return source;
	}
	
	@Bean
	public Md5PasswordEncoder passEncoder()
	{
		return new Md5PasswordEncoder();
	}
	
	@Bean 
	public DaoAuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setSaltSource(saltSource);
	    provider.setUserDetailsService(userDetailsService);
	    provider.setPasswordEncoder(passEnc);
	    
	    return provider;
	}
	
	@Bean 
	public AccountModel accountModel()
	{   
	    return new AccountModel();
	}
	
}
