package org.ericgha.reactivetodolist.controllers;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoListRepository;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.netty.http.server.HttpServerResponse;

import java.time.Duration;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("api/list")
@RequiredArgsConstructor
public class ListController {

    private final ToDoListRepository toDoListRepository;

    @PostMapping("")
    Mono<ToDoList> addList(@RequestBody ToDoList toDoList) {
        return toDoListRepository.save(toDoList);
    }

    @PutMapping("")
    Mono<ToDoList> updateList(@RequestBody ToDoList toDoList, ServerHttpResponse response) {
        // put something in here to verify userId also on repo side
        return toDoListRepository.save(toDoList);
    }

    @GetMapping("")
    Flux<ToDoList> getLists(@AuthenticationPrincipal Mono<ToDoUser> toDoUser) {
        return toDoUser.map(ToDoUser::getUserId)
                       .flatMapMany(toDoListRepository::findByUserId);
    }

    @DeleteMapping("")
    Mono<Long> deleteItem(@RequestBody ToDoList list, ServerHttpResponse response) {
        return toDoListRepository.deleteById(list.getListId() )
                .timeout(Duration.ofSeconds(5) )
                .doOnError( e -> response.setStatusCode( BAD_REQUEST ) )
                .onErrorResume( e -> Mono.empty() )
                .map( v -> list.getListId() );
    }
}
