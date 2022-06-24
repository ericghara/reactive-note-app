package org.ericgha.reactivetodolist.configs;

import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.services.ToDoUserJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    @Autowired
    ToDoUserJwtAuthenticationConverter toDoUserJwtAuthenticationConverter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf()
                .disable()
                .authorizeExchange()
                    .matchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(toDoUserJwtAuthenticationConverter);
        return http.build();
    }
}
