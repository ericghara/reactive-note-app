package org.ericgha.reactivetodolist.repository;

import org.ericgha.reactivetodolist.dtos.ToDoItem;
import org.ericgha.reactivetodolist.dtos.ToDoList;
import org.ericgha.reactivetodolist.services.ItemService;
import org.ericgha.reactivetodolist.services.ListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DataR2dbcTest
class ToDoItemRepositoryTest {

    static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>( DockerImageName.parse( "postgres:14" ) )
                .withDatabaseName( "test_db" )
                .withUsername( "admin" )
                .withPassword( "password" )
                .withReuse( true )
                .withInitScript( "schema.sql" );
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add( "spring.r2dbc.url", () -> postgreSQLContainer.getJdbcUrl().replace( "jdbc:", "r2dbc:" ) );
        registry.add( "spring.r2dbc.password", postgreSQLContainer::getPassword );
        registry.add( "spring.r2dbc.username", postgreSQLContainer::getUsername );
    }

    @Autowired
    ToDoItemRepository toDoItemRepository;
    @Autowired
    ToDoListRepository toDoListRepository;

    Long curListId;

    @BeforeEach
    void beforeEach() {
        var toDoList = new ToDoList();
        toDoList.setUserId( "0" );
        toDoList.setListName( "testList" );
        toDoListRepository.save( toDoList )
                .doOnSuccess( list -> curListId = list.getListId() )
                .block();
    }

    @Test
    void decrementLargerItemIndexesThanItem() throws InterruptedException {
        // 1 indexed b/c sql
        Flux<ToDoItem> items = Flux.fromStream( IntStream.range( 1, 6 ).boxed() )
                .map( i -> {
                    ToDoItem item = new ToDoItem();
                    item.setListId( curListId );
                    item.setItemIndex( i );
                    item.setItemText( i.toString() );
                    System.out.println( item );
                    return item;
                } );
        toDoItemRepository.saveAll( items ).blockLast();

        // decrement anything with listId(==listIndex) > 2
        Flux<Integer> decremented = toDoItemRepository.decrementLargerItemIndexesThanItem( 2L, curListId )
                .flatMapMany( cnt -> toDoItemRepository.findByListId( curListId ) )
                .map( ToDoItem::getItemIndex );

        List<Integer> expected = List.of( 1, 2, 2, 3, 4 );
        List<Integer> found = new ArrayList<>( 5 );
        decremented.map( found::add ).blockLast();
        found.sort( Integer::compare );
        assertIterableEquals( expected, found );
    }
}