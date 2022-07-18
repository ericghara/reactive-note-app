package org.ericgha.reactivetodolist.repository;

import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ToDoItemRepository extends R2dbcRepository<ToDoItem, Long> {

    Mono<ToDoItem> findByItemId(Long itemId);

    Flux<ToDoItem> findByListId(Long listId);

    Mono<Long> countToDoItemByListId(Long listId);

    @Modifying
    @Query("""
            UPDATE to_do_items
            SET item_text = :itemText
            WHERE item_id = :itemId AND item_index = :itemIndex;
            """)
    Mono<Long> updateItemTextByItemIdAndItemIndex(String text,
                                                  Long itemId,
                                                  Integer itemIndex);

    @Modifying
    @Query("""
                UPDATE to_do_items
                SET item_index = item_index - 1
                WHERE list_id = :listId
                    AND item_index > (SELECT item_index
                                      FROM to_do_items
                                      WHERE  item_id = :itemId);
            """)
    Mono<Long> decrementLargerItemIndexesThanItem(Long itemId, Long listId);
}
