package org.ericgha.reactivetodolist.controllers;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoListRepository;
import org.ericgha.reactivetodolist.services.ListService;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("api/list")
@RequiredArgsConstructor
public class ListController {

    private final ToDoListRepository toDoListRepository;
    private final ListService listService;

    @PostMapping("")
    Mono<ToDoList> addList(@RequestBody ToDoList toDoList) {
        return listService.addList(toDoList);
    }

    @PutMapping("")
    Mono<ToDoList> updateList(@RequestBody ToDoList toDoList, ServerHttpResponse response) {
        return listService.updateList( toDoList );
    }

    @GetMapping("")
    Flux<ToDoList> getLists(@AuthenticationPrincipal Mono<ToDoUser> toDoUser) {
        return listService.getListsFor( toDoUser );
    }

    @DeleteMapping("")
    Mono<Long> deleteList(@RequestBody ToDoList toDoList, ServerHttpResponse response) {
        return listService.deleteList( toDoList )
                .doOnError( e -> response.setStatusCode( BAD_REQUEST ) )
                .onErrorResume( e -> Mono.empty() )
                .map( v -> toDoList.getListId() );
    }
}
