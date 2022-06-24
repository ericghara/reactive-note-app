package org.ericgha.reactivetodolist.repository;

import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ToDoItemRepository extends R2dbcRepository<ToDoItem, Long> {

    Mono<ToDoItem> findByItemId(Long itemId);

    Flux<ToDoItem> findByListId(Long listId);
}
