package org.ericgha.reactivetodolist.configs;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class StaticRoutesConfig {

    static final Path STATIC_DIR = Path.of("/www");

    @Bean
    @Profile( "prod" )
    public RouterFunction<ServerResponse> staticRouter() {
        return RouterFunctions.resources("/**", new PathResource( STATIC_DIR ) );
    }

    @Bean
    @Profile( "prod" )
    public RouterFunction<ServerResponse> indexRouter() {
        return route(GET("/"), request ->
                ServerResponse.temporaryRedirect( URI.create("/index.html") )
                        .build() );
    }
}
