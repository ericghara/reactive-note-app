package org.ericgha.reactivetodolist.controllers;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.repository.ToDoItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item")
public class ItemController {

    private final ToDoItemRepository toDoItemRepository;

    @GetMapping("/hello/{name}")
    Mono<String> sayHello(@PathVariable("name") String name) {
        return Mono.just("Hello " + name + ".");
    }

    @PostMapping("")
    Mono<ToDoItem> addPost(@RequestBody ToDoItem item) {
        return toDoItemRepository.save(item);
    }

//    @GetMapping("")
//    Mono<ToDoItem> getItem(@RequestParam long itemId) {
//        return toDoItemRepository.findByItemId(itemId);
//    }

    @DeleteMapping("")
    Mono<Boolean> deleteItem(@RequestBody ToDoItem item) {
        Sinks.One<Boolean> sink = Sinks.one();
        toDoItemRepository.deleteById(item.getItemId() )
                          .timeout(Duration.ofSeconds(5) )
                          .doOnSuccess(s -> sink.tryEmitValue(true) )
                          .doOnError(e -> sink.tryEmitValue(false) )
                          .subscribe();
        return sink.asMono();
    }

    @GetMapping("")
    @CrossOrigin
    Flux<ToDoItem> getItems(@RequestParam Optional<Long> listId, ServerHttpResponse response) {
        if (listId.isEmpty() ) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Flux.empty();
        }
        return toDoItemRepository.findByListId(listId.get() ).onErrorResume( e -> Flux.empty() );
    }
}
