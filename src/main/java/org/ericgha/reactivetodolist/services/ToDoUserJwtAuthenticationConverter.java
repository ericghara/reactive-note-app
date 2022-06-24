package org.ericgha.reactivetodolist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoUserJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_PREFIX = "ROLE_";
    private final JwtUserDetailsService userDetailsService;

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        Collection<GrantedAuthority> authorities = extractAuthorities( source );
        return Mono.just(new UsernamePasswordAuthenticationToken(userDetailsService.convert(source),
                "n/a", authorities) );
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Mono<AbstractAuthenticationToken>, ? extends U> after) {
        return Converter.super.andThen( after );
    }

    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.getScopes( jwt )
                .stream()
                .map( s -> ROLE_PREFIX + s.toUpperCase() )
                .map( SimpleGrantedAuthority::new )
                .map( a -> (GrantedAuthority) a )
                .toList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> getScopes(Jwt jwt) {
        Object scopes = jwt.getClaims().get( JwtClaim.REALM_ACCESS.key() );
        if (scopes instanceof Collection) {
            return (Collection<String>) scopes;
        }
        return Collections.emptyList();
    }
}
