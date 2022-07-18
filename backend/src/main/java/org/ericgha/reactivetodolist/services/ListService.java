package org.ericgha.reactivetodolist.services;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoListRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ListService {

    final static String AUTH_CHECK = "hasAuthority(#toDoList.userId)";
    private final ToDoListRepository toDoListRepository;

    @PreAuthorize(AUTH_CHECK)
    public Mono<ToDoList> addList(ToDoList toDoList) {
        return toDoListRepository.save( toDoList );
    }

    @PreAuthorize(AUTH_CHECK)
    public Mono<ToDoList> updateList(ToDoList toDoList) {
        // the update by UserId and... in conjunction with the preAuth check
        // serves to prevent user a from modifying user b's list
        return toDoListRepository.updateByUserIdAndListId(
                toDoList.getUserId(),
                toDoList.getListId(),
                toDoList.getListName() ).map( l -> {
                    if (l > 0) {
                        return toDoList;
                    }
                    throw new NoSuchElementException();
                }
        );
    }

    public Flux<ToDoList> getListsFor(Mono<ToDoUser> toDoUser) {
        return toDoUser.map( ToDoUser::getUserId )
                .flatMapMany( toDoListRepository::findByUserId );
    }

    public Mono<Long> userOwnsList(Long listId, Mono<ToDoUser> toDoUser) {
        return toDoUser.flatMap( u -> toDoListRepository.countToDoListByUserIdAndListId( u.getUserId(), listId ) )
                .map( c -> {
                    if (c > 0) {
                        return listId;
                    } else {
                        throw new SecurityException();
                    }
                } );
    }

    @PreAuthorize(AUTH_CHECK)
    public Mono<Long> deleteList(ToDoList toDoList) {
        return toDoListRepository.deleteByUserIdAndListId( toDoList.getUserId(), toDoList.getListId() )
                .timeout( Duration.ofSeconds( 5 ) );
    }
}
