package org.ericgha.reactivetodolist.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
//@Import(ReactiveOAuth2ClientAutoConfiguration.class)
public class WebSecurityConfiguration {


    @Autowired
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtConverter;

    @Bean
    public CorsConfigurationSource corsConfigurationSources() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins( Arrays.asList( "*" ) );   // change this in future
        configuration.setAllowCredentials( true );
        configuration.setAllowedOrigins( List.of("https://auth.ericgha.com/",
                                                        "http://localhost:8080/",
                                                        "http://localhost:3000/",
                                                        "http://127.0.0.1:3000/",
                                                        "http://127.0.0.1:8080/") );
        configuration.setAllowedMethods( Arrays.asList( "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS" ) );
//        configuration.setAllowedHeaders( Arrays.asList( "authorization", "content-type", "x-auth-token" ) );
        configuration.setAllowedHeaders( Collections.singletonList("*") );
//        configuration.setExposedHeaders( Arrays.asList( "x-auth-token" ) );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", configuration );
        source.registerCorsConfiguration( "https://auth.ericgha.com/**", configuration);
        return source;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf()
                .disable() //revisit
//                .authorizeExchange()
//                .anyExchange()
//                .permitAll()
                .authorizeExchange()
                .matchers( PathRequest.toStaticResources().atCommonLocations() )
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
