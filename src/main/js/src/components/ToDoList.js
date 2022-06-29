import {useEffect} from "react"
import HttpService from "../services/HttpService"
import { ToDoListStyled, ToDoCardStyled, ToDoListHeaderStyled } from "./styles/ToDoListContainer.styled"

const ToDoItem = ({itemId, listId, itemIndex, itemText}) =>
    <ToDoCardStyled>
            {itemText}
    </ToDoCardStyled>
    

const ToDoList = ({list, items, setItems}) => {
    useToDoList(list, setItems)
    return (
        <ToDoListStyled>
        <div>
            <ToDoListHeaderStyled>{list.listName}</ToDoListHeaderStyled>
        </div>

                {items.map(({itemId, listId, itemIndex, itemText}) => 
                    <ToDoItem itemId={itemId}
                            listId={listId}
                            itemIndex={itemIndex}
                            itemText={itemText}
                            key={itemId} />
                    )}
            
        
        </ToDoListStyled>
    )
}

const useToDoList = ({listId}, setItems) => {
    useEffect( () => { 
            HttpService.getClient().get(HttpService.getEndpoint("getItems"), {params: {listId}} ) // modify
            .then(response => response.data.sort( (a,b) => a.itemIndex - b.itemIndex ) )
            .then(items => setItems(items) )
            .catch(e => { setItems([]) 
                        console.info("To Do List Fetch Failure") 
                    } ) },
            [setItems, listId]
    );
} 

export {ToDoList, useToDoList}