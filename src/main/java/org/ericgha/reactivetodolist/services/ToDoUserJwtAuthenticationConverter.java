package org.ericgha.reactivetodolist.configs;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.security.ToDoReactiveUserDetailsService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ToDoUserJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        Collection<GrantedAuthority> authorities = extractAuthorities(source);

    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Mono<AbstractAuthenticationToken>, ? extends U> after) {
        return Converter.super.andThen(after);
    }

    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.getScopes(jwt)
                    .stream()
                    .map( s -> ROLE_PREFIX + s.toUpperCase() )
                    .map(SimpleGrantedAuthority::new)
                    .map( a -> (GrantedAuthority) a )
                    .toList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> getScopes(Jwt jwt) {
        Object scopes = jwt.getClaims().get(GROUPS_CLAIM);
        if (scopes instanceof Collection) {
            return (Collection<String>) scopes;
        }
        return Collections.emptyList();
    }
}
