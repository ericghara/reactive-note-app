package org.ericgha.reactivetodolist.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "to_do_items")
public class ToDoItem {

    @Id
    private Long itemId;
    private Long listId;
    private Integer itemIndex;
    private String itemText;

}
