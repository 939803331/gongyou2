package com.ph.securityoauth2test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @PACKAGE_NAME: com.ph.securityoauth2test.config
 * @NAME: OAuth2ServerConfig
 * @Description: 认证服务器
 * @Author ph
 * @DATE: 2019-08-23 10:04
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
				.realm("oauth2-resources")
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("helloApp")
				.secret(passwordEncoder.encode("helloAppSecret"))
				.redirectUris("www.baidu.com")
				.authorizedGrantTypes("authorization_code", "client_credentials", "password", "refresh_token")
				.scopes("all")
				.resourceIds("oauth2-resource")
				.accessTokenValiditySeconds(1200)
				.refreshTokenValiditySeconds(50000);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				//允许get,post请求获取token,访问断点oauth/token
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}
}




















