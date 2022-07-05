import {useEffect} from "react"
import HttpService from "../services/HttpService"
import { ToDoListStyled, ToDoCardStyled } from "./styles/ToDoListContainer.styled"
import {AddItem} from "./AddItem";
import {CurListHeader} from "./ToDoListHeader";

const ToDoList = ({listProps, items, setItems}) => {
    useToDoList(listProps.curList, setItems)
    return (
        <ToDoListStyled>
            <CurListHeader stateProps={listProps}/>
            {console.log(items)}
                {items.map(({itemId, listId, itemIndex, itemText}) => 
                    <ToDoItem itemId={itemId}
                            listId={listId}
                            itemIndex={itemIndex}
                            itemText={itemText}
                            key={itemId} />
                    )}
            <AddItem items={items} setItems={setItems} />
        </ToDoListStyled>
    )
}

const ToDoItem = ({itemId, listId, itemIndex, itemText}) =>
    <ToDoCardStyled>
        {itemText}
    </ToDoCardStyled>

const useToDoList = (curList, setItems) => {
    useEffect( () => { 
            HttpService.getClient().get(HttpService.getEndpoint("getItems"), {params: {listId : curList.listId}} ) // modify
            .then(response => response.data.sort( (a,b) => a.itemIndex - b.itemIndex ) )
            .then(items => setItems(items) )
            .catch(e => { setItems([]) 
                        console.info("To Do List Fetch Failure") 
                    } ) },
            [setItems, curList]
    );
} 

export {ToDoList, useToDoList}