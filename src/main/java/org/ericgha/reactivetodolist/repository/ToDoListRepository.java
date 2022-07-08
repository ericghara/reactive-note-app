package org.ericgha.reactivetodolist.repository;

import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ToDoListRepository extends R2dbcRepository<ToDoList, Long> {

    Mono<ToDoList> findByListId(Long listId);

    Flux<ToDoList> findByUserId(String userId);

    @Modifying
    @Query( """
            UPDATE to_do_lists
            SET list_name =  :listName
            WHERE user_id = :userId AND list_id = :listId;
            """ )
    Mono<Long> updateByUserIdAndListId(String userId, long listId, String listName);

    Mono<Long> countToDoListByUserIdAndListId(String userId, long listId);

    Mono<Long> deleteByUserIdAndListId(String userId, long listId);
}
