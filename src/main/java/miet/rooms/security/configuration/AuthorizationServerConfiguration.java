package miet.rooms.security.configuration;

import javax.sql.DataSource;

import miet.rooms.security.service.CrmUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//    private static String REALM = "CRM_REALM";

    private final DataSource dataSource;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter jwtTokenEnhancer;
    private final UserApprovalHandler userApprovalHandler;
    private final AuthenticationManager authenticationManager;
    private final CrmUserDetailsService crmUserDetailsService;

    @Autowired
    public AuthorizationServerConfiguration(DataSource dataSource,
                                            TokenStore tokenStore,
                                            JwtAccessTokenConverter jwtTokenEnhancer,
                                            UserApprovalHandler userApprovalHandler,
                                            @Qualifier("authenticationManagerBean")
                                             AuthenticationManager authenticationManager,
                                            CrmUserDetailsService crmUserDetailsService) {
        this.dataSource = dataSource;
        this.tokenStore = tokenStore;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
        this.userApprovalHandler = userApprovalHandler;
        this.authenticationManager = authenticationManager;
        this.crmUserDetailsService = crmUserDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

	/*
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
		.authenticationManager(authenticationManager)
		.userDetailsService(crmUserDetailsService);
	}
	*/

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).tokenEnhancer(jwtTokenEnhancer).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .userDetailsService(crmUserDetailsService);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer.realm(REALM);
        oauthServer.allowFormAuthenticationForClients();
    }
}