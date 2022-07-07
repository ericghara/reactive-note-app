package org.ericgha.reactivetodolist.services;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
/**
 * Creates ToDoUser from client_scopes encoded in jwt
 */
public class JwtToDoUserConverter implements Converter<Jwt, ToDoUser> {

    @Override
    public ToDoUser convert(Jwt source) {
        return ToDoUser.builder()
                .userId( source.getClaimAsString( JwtClaim.USER_ID.key() ) )
                .username( source.getClaimAsString( JwtClaim.USERNAME.key() ) )
                .email( source.getClaimAsString( JwtClaim.EMAIL.key() ) )
                .emailVerified( source.getClaimAsBoolean( JwtClaim.EMAIL_VERIFIED.key() ) )
                .fullName( source.getClaimAsString( JwtClaim.FULL_NAME.key() ) )
                .firstName( source.getClaimAsString( JwtClaim.FIRST_NAME.key() ) )
                .lastName( source.getClaimAsString( JwtClaim.LAST_NAME.key() ) )
                .realm( source.getClaimAsString( JwtClaim.REALM.key() ) )
                .build();
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super ToDoUser, ? extends U> after) {
        return Converter.super.andThen( after );
    }
}
