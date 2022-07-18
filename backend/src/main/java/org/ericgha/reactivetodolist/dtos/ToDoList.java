package org.ericgha.reactivetodolist.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "to_do_lists")
public class ToDoList {

    @Id
    private Long listId;
    private String userId;
    private String listName;

    public void setUserId(String userId) {
        this.userId = userId.toLowerCase();
    }

}
