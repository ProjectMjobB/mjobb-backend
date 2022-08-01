package com.mjobb.configuration;

import com.mjobb.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String PASSWORD = "password";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String REFRESH_TOKEN = "refresh_token";

    @Value("${oauth2.client.id}")
    private String client;

    @Value("${oauth2.secret.id}")
    private String secret;

    @Value("${oauth2.scopes}")
    private String scopes;

    @Value("${oauth2.access.token.valid.seconds}")
    private String accessTokenValidSeconds;

    @Value("${oauth2.refresh.token.valid.seconds}")
    private String refreshTokenValidSeconds;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsServiceImpl userDetailsService;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(client)
                .secret(passwordEncoder.encode(secret))
                .authorizedGrantTypes(PASSWORD, CLIENT_CREDENTIALS, REFRESH_TOKEN)
                .scopes(scopes)
                .accessTokenValiditySeconds(Integer.parseInt(accessTokenValidSeconds))
                .refreshTokenValiditySeconds(Integer.parseInt(refreshTokenValidSeconds));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
}
