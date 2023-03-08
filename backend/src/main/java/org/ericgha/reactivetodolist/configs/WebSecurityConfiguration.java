package org.ericgha.reactivetodolist.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    @Autowired
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtConverter;

    @Value("${app.security.allowed-origins}")
    private String originStr;

    private List<String> getOrigins() {
        return List.of(originStr.split(",\\s") );
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSources() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials( true );
        configuration.setAllowedOrigins( this.getOrigins() );
        configuration.setAllowedMethods( Arrays.asList( "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS" ) );
        configuration.setAllowedHeaders( Collections.singletonList("*") );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", configuration );
        source.registerCorsConfiguration( "https://auth.ericgha.com/**", configuration);
        return source;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .headers()
                .frameOptions()
                .mode( XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)  // frontend requires to check sso status
                .and()
                .csrf()
                .disable()
                .authorizeExchange()
                .matchers( PathRequest.toStaticResources().atCommonLocations() )
                .permitAll()
                .pathMatchers(GET, "/*", "/icons/**", "/static/**", "/actuator/health", "/api/list", "/api/item" )
                .permitAll()
                .pathMatchers(  "/api/list**", "/api/item**" )
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter( jwtConverter );
        return http.build();
    }
}
