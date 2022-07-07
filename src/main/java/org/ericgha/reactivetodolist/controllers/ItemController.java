package org.ericgha.reactivetodolist.controllers;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item")
public class ItemController {

    private final ToDoItemRepository toDoItemRepository;


    @PostMapping("")
    Mono<ToDoItem> addItem(@RequestBody ToDoItem item) {
        return toDoItemRepository.save(item);
    }

    @PutMapping("")
    Mono<ToDoItem> updateList(@RequestBody ToDoItem item, ServerHttpResponse response) {
        // put something in here to verify userId also on repo side
        return toDoItemRepository.save(item);
    }

//    @GetMapping("")
//    Mono<ToDoItem> getItem(@RequestParam long itemId) {
//        return toDoItemRepository.findByItemId(itemId);
//    }

    @DeleteMapping("")
    Mono<Long> deleteItem(@RequestBody ToDoItem item, ServerHttpResponse response) {
        return toDoItemRepository.deleteById(item.getItemId() )
                .timeout(Duration.ofSeconds(5) )
                .doOnError( e -> response.setStatusCode( BAD_REQUEST ) )
                .onErrorResume( e -> Mono.empty() )
                          .map( v -> item.getItemId() );
    }

    @GetMapping("")
    Flux<ToDoItem> getItems(@RequestParam Optional<Long> listId,
                            ServerHttpResponse response,
                            @AuthenticationPrincipal Mono<ToDoUser> principal) {
        principal.subscribe(System.out::println);
        if (listId.isEmpty() ) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Flux.empty();
        }
        return toDoItemRepository.findByListId(listId.get() ).onErrorResume( e -> Flux.empty() );
    }
}
