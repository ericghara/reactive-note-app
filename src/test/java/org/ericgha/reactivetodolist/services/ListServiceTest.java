package org.ericgha.reactivetodolist.services;

import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.repository.ToDoListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {ListService.class} )
@EnableReactiveMethodSecurity
class ListServiceTest {

    @MockBean
    ToDoListRepository toDoListRepository;
    @Mock
    ToDoList toDoList;

    @Autowired
    ListService listService;

    final static String userId = "0123456789abcdef";

    @Test
    @WithMockUser(authorities = {userId})
    void preAuthorizeAllowsUserWithUserIdAuthority() {
        when(toDoList.getUserId() ).thenReturn( userId );
        when(toDoListRepository.save( any(ToDoList.class) ) ).thenReturn( Mono.just(toDoList) );
        StepVerifier.create( listService.addList(toDoList) )
                .expectNext( toDoList )
                .verifyComplete();
    }

    @Test
    @WithMockUser(authorities = {userId})
    void preAuthorizeDeniesUserWithoutUserIdAuthority() {
        when(toDoList.getUserId() ).thenAnswer( (x) -> new StringBuilder(userId).reverse().toString() );
        when(toDoListRepository.save( any(ToDoList.class) ) ).thenReturn( Mono.just(toDoList) );
        StepVerifier.create( listService.addList(toDoList) )
                .expectError( AccessDeniedException.class )
                .verify();
    }

    @Test
    @WithMockUser(authorities = {userId})
    void updateListThrowsWhenNoUpdate() {
        when( toDoList.getUserId() ).thenReturn( userId );
        when( toDoList.getListId() ).thenReturn( 0L );
        when( toDoList.getListName() ).thenReturn( "test" );
        when( toDoListRepository.updateByUserIdAndListId( anyString(), anyLong(), anyString() ) )
                .thenReturn( Mono.just( 0L ) );
        StepVerifier.create( listService.updateList( toDoList ) )
                .expectError( NoSuchElementException.class )
                .verify();
    }

    @Test
    @WithMockUser(authorities = {userId})
    void updateListReturnsToDoListWhenUpdate() {
        when( toDoList.getUserId() ).thenReturn( userId );
        when( toDoList.getListId() ).thenReturn( 0L );
        when( toDoList.getListName() ).thenReturn( "test" );
        when( toDoListRepository.updateByUserIdAndListId( anyString(), anyLong(), anyString() ) )
                .thenReturn( Mono.just( 1L ) );
        StepVerifier.create( listService.updateList( toDoList ) )
                .expectNext(toDoList)
                .verifyComplete();
    }


}