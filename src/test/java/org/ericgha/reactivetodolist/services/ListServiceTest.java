package org.ericgha.reactivetodolist.services;

import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.repository.ToDoListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
}