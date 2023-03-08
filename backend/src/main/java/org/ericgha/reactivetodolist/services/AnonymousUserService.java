package org.ericgha.reactivetodolist.services;

import lombok.Getter;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AnonymousUserService {

    @Getter
    private final Mono<ToDoUser> toDoUser;

    @Autowired
    public AnonymousUserService(Environment environment) {
        this.toDoUser = generateAnonymousUser( environment );
    }

    private Mono<ToDoUser> generateAnonymousUser(Environment environment) {
        String anonymousUserId = environment.getProperty( "app.security.anonymous-user-id" );
        String anonymousUsername = environment.getProperty( "app.security.anonymous-username" );
        String issuerUri = environment.getProperty( "spring.security.oauth2.client.provider.ToDo.issuer-uri" );
        if (Objects.isNull(anonymousUsername) || Objects.isNull( anonymousUserId ) || Objects.isNull( issuerUri ) ) {
            throw new IllegalArgumentException( "Unable to generate anonymous user due to a null application property");
        }
        String realm = parseRealm( issuerUri );
        return Mono.just(ToDoUser.builder()
                .userId( anonymousUserId )
                .username( anonymousUsername )
                .realm(realm)
                .build() );
    }

    private String parseRealm(String issuerUri) {
        return issuerUri.split(".+/realms/")[0];
    }
}
