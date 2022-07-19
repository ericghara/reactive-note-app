package org.ericgha.reactivetodolist.services;

import lombok.RequiredArgsConstructor;
import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ToDoItemRepository toDoItemRepository;
    private final ListService listService;
    private final AnonymousUserService anonymousUserService;

    @Transactional
    public Mono<ToDoItem> updateItemText(ToDoItem item, Mono<ToDoUser> toDoUser) {
        return listService.userOwnsList( item.getListId(), toDoUser )
                .flatMap( l -> toDoItemRepository.updateItemTextByItemIdAndItemIndex( item.getItemText(),
                        item.getItemId(),
                        item.getItemIndex() ) )
                .map( l -> {
                    if (l > 0) {
                        return item;
                    }
                    throw new IllegalArgumentException( "Unable to update item. Item Id or Item index is invalid." );
                } );
    }

    @Transactional
    public Mono<ToDoItem> addItem(ToDoItem item, Mono<ToDoUser> toDoUser) {
        return listService.userOwnsList( item.getListId(), toDoUser )
                .flatMap( l -> toDoItemRepository.countToDoItemByListId( item.getListId() ) )
                .map(l -> l.equals( (long) item.getItemIndex() ) )
                .map(b -> {
                    if (b) {
                        return b;
                    }
                    throw new IllegalArgumentException("Invalid item index");
                })
                .flatMap( b -> toDoItemRepository.save(item) );
    }

    @Transactional(readOnly = true)
    public Flux<ToDoItem> getItems(Long listId, Mono<ToDoUser> toDoUser) {
        if (Objects.isNull(toDoUser) ) {
            toDoUser = anonymousUserService.getToDoUser();
        }
        return listService.userOwnsList( listId, toDoUser )
                          .flatMapMany( toDoItemRepository::findByListId );
    }

    @Transactional
    public Mono<Void> deleteItem(ToDoItem item, Mono<ToDoUser> toDoUser) {
        return listService.userOwnsList(item.getListId(), toDoUser )
                .flatMap( l -> toDoItemRepository.decrementLargerItemIndexesThanItem(item.getItemId(), l) )
                .flatMap( l -> toDoItemRepository.deleteById( item.getItemId() ) );
    }
}
