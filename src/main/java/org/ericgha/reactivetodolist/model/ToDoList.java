package org.ericgha.reactivetodolist.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Table(name = "to_do_lists")
public class ToDoList {

    @Id
    private Long listId;
    private Long userId;
    private String listName;

}
