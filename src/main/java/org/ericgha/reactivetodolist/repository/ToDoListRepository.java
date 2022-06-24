package org.ericgha.reactivetodolist.repository;

import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ToDoListRepository extends R2dbcRepository<ToDoList, Long> {

    Mono<ToDoList> findByListId(Long listId);

    Flux<ToDoList> findByUserId(Long userId);

}
