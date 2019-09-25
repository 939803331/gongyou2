package com.ph.securityoauth2test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @PACKAGE_NAME: com.ph.securityoauth2test.config
 * @NAME: ResourceServerConfig
 * @Description: 资源服务器
 * @Author ph
 * @DATE: 2019-08-23 10:20
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
				.antMatchers("/api/**")
				.and()
				.authorizeRequests()
				.antMatchers("/api/**")
				.authenticated();
	}
}























