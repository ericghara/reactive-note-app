package org.ericgha.reactivetodolist.configs;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ericgha.reactivetodolist.services.ToDoUserJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.expression.ParseException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
//@Import(ReactiveOAuth2ClientAutoConfiguration.class)
public class WebSecurityConfiguration {


    private final ToDoUserJwtAuthenticationConverter toDoUserJwtAuthenticationConverter;

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
                .disable()
//                .authorizeExchange()
//                .anyExchange()
//                .permitAll()
                .authorizeExchange()
                .matchers( PathRequest.toStaticResources().atCommonLocations() )
                .permitAll()
                .pathMatchers(  "/api/list**", "/api/item**" )
                .authenticated()

                .and()
                .oauth2Login()
                .and()
                .oauth2Client( Customizer.withDefaults() )
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter( toDoUserJwtAuthenticationConverter );
        return http.build();
    }

}
