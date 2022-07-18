package org.ericgha.reactivetodolist.services;

import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoUser;
import org.ericgha.reactivetodolist.repository.ToDoItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;
import java.util.Stack;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ItemService.class})
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @MockBean
    ListService listService;

    @MockBean
    ToDoItemRepository toDoItemRepository;

    @Mock
    ToDoUser toDoUser;

    @Mock
    ToDoItem toDoItem;

    @Test
    void updateItemTextThrowsWhenNoUpdate() {
        when( listService.userOwnsList( anyLong(), ArgumentMatchers.<Mono<ToDoUser>>any() ) )
                .thenReturn( Mono.just( 1L ) );
        when( toDoItemRepository.updateItemTextByItemIdAndItemIndex( anyString(), anyLong(), anyInt() ) )
                .thenReturn( Mono.just( 0L ) ); // no update
        when( toDoItem.getItemId() ).thenReturn( 0L );
        when( toDoItem.getItemIndex() ).thenReturn( 0 );
        when( toDoItem.getItemText() ).thenReturn( "" );
        Mono<ToDoItem> shouldThrow = itemService.updateItemText( toDoItem, Mono.just( toDoUser ) );
        StepVerifier.create( shouldThrow )
                .expectError( IllegalArgumentException.class )
                .verify();
    }

    @Test
    void updateItemTextReturnsItemWhenUpdate() {
        when( listService.userOwnsList( anyLong(), ArgumentMatchers.<Mono<ToDoUser>>any() ) )
                .thenReturn( Mono.just( 1L ) );
        when( toDoItemRepository.updateItemTextByItemIdAndItemIndex( anyString(), anyLong(), anyInt() ) )
                .thenReturn( Mono.just( 1L ) ); // 1 update
        when( toDoItem.getItemId() ).thenReturn( 0L );
        when( toDoItem.getItemIndex() ).thenReturn( 0 );
        when( toDoItem.getItemText() ).thenReturn( "" );
        Mono<ToDoItem> shouldReturnItem = itemService.updateItemText( toDoItem, Mono.just( toDoUser ) );
        StepVerifier.create( shouldReturnItem )
                .expectNext( toDoItem )
                .verifyComplete();
    }

    @Test
    void addItemThrowsWhenItemIndexNotEqCountItems() {
        when( listService.userOwnsList( anyLong(), ArgumentMatchers.<Mono<ToDoUser>>any() ) )
                .thenReturn( Mono.just( 1L ) );
        when( toDoItemRepository.countToDoItemByListId( anyLong() ) )
                .thenReturn( Mono.just( 0L ) );
        when(toDoItem.getItemIndex() ).thenReturn( 1 );  // index > count

        StepVerifier.create( itemService.addItem(toDoItem, Mono.just(toDoUser ) ) )
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void addItemSavesWhenItemIndexEqCountItems() {
        when( listService.userOwnsList( anyLong(), ArgumentMatchers.<Mono<ToDoUser>>any() ) )
                .thenReturn( Mono.just( 1L ) );
        when( toDoItemRepository.countToDoItemByListId( anyLong() ) )
                .thenReturn( Mono.just( 0L ) );
        when(toDoItem.getItemIndex() )
                .thenReturn( 0 ); // index == count
        when(toDoItemRepository.save(any(ToDoItem.class) ) )
                        .thenReturn( Mono.just(toDoItem) );

        ArgumentCaptor<ToDoItem> captor = ArgumentCaptor.forClass(ToDoItem.class);
        Mono<ToDoItem> found = itemService.addItem( toDoItem, Mono.just(toDoUser) );
        StepVerifier.create( found )
                .expectNext( toDoItem )
                .verifyComplete();
        verify(toDoItemRepository ).save(captor.capture() );
    }
}