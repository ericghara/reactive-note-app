package org.ericgha.reactivetodolist.services;

import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {JwtToAuthenticationTokenConverter.class})
class JwtToAuthenticationTokenConverterTest {

    @MockBean
    JwtToDoUserConverter jwtToDoUserConverter;
    @Mock
    Jwt jwt;
    @Mock
    ToDoUser principal;
    @Autowired
    JwtToAuthenticationTokenConverter jwtToAuthConverter;

    @Test
    void convert() {
        when( principal.getUserId() ).thenReturn( "test" );
        when( jwtToDoUserConverter.convert( any( Jwt.class ) ) )
                .thenReturn( principal );
        when( jwt.getClaims() ).thenReturn( Map.of( "scope", "openid email profile" ) );

        AbstractAuthenticationToken token = jwtToAuthConverter.convert( jwt ).block();
        assertEquals( "n/a", token.getCredentials(), "Verify Token Credentials" );
        assertEquals( principal, token.getPrincipal(), "Verify Expected Principal" );
        List<String> expectedAuth = Stream.of( "ROLE_OPENID", "ROLE_EMAIL", "ROLE_PROFILE", "test" )
                .sorted()
                .toList();
        List<String> foundAuth = token.getAuthorities()
                .stream()
                .map( GrantedAuthority::toString )
                .sorted()
                .toList();
        assertIterableEquals( expectedAuth, foundAuth, "Verify Expected Authorities" );
    }
}