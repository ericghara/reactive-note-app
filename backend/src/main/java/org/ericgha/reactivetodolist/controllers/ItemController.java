package org.ericgha.reactivetodolist.controllers;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoItemRepository;
import org.ericgha.reactivetodolist.services.ItemService;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item")
public class ItemController {

    private final ToDoItemRepository toDoItemRepository;
    private final ItemService itemService;


    @PostMapping("")
    Mono<ToDoItem> addItem(@RequestBody ToDoItem item,
                           @AuthenticationPrincipal Mono<ToDoUser> toDoUser) {
        return itemService.addItem(item, toDoUser);
    }

    @PutMapping("")
    Mono<ToDoItem> updateItemText(@RequestBody ToDoItem item,
                              @AuthenticationPrincipal Mono<ToDoUser> toDoUser) {
        return itemService.updateItemText(item, toDoUser);
    }

    @DeleteMapping("")
    Mono<Long> deleteItem(@RequestBody ToDoItem item,
                          ServerHttpResponse response,
                          @AuthenticationPrincipal Mono<ToDoUser> toDoUser) {
        return itemService.deleteItem( item, toDoUser )
                .timeout(Duration.ofSeconds(5) )
                .doOnError( e -> response.setStatusCode( BAD_REQUEST ) )
                .onErrorResume( e -> Mono.empty() )
                          .map( v -> item.getItemId() );
    }

    @GetMapping("")
    Flux<ToDoItem> getItems(@RequestParam Long listId,
                            ServerHttpResponse response,
                            @AuthenticationPrincipal Mono<ToDoUser> toDoUser) {
        return itemService.getItems(listId, toDoUser);
    }
}
