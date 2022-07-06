import {useEffect} from "react"
import HttpService from "../services/HttpService"
import {ToDoListStyled, ToDoCardStyled} from "./styles/ToDoListContainer.styled"
import {AddItem} from "./AddItem";
import {CurListHeader} from "./ToDoListHeader";
import {ToDoItem} from "./ToDoItem";

const ToDoList = ({listProps, items, setItems}) => {
    useToDoList(listProps.curList, setItems)
    if (listProps.curList?.listId === undefined) {
        return null
    }
    return (
        <ToDoListStyled>
            <CurListHeader stateProps={listProps}/>
            {items.map(({itemId, listId, itemIndex, itemText}) =>
                <ToDoItem items={items}
                          setItems={setItems}
                          listId={listId}
                          itemIndex={itemIndex}
                          itemText={itemText}
                          itemId={itemId}
                          key={itemId}/>
            )}
            <AddItem listId={listProps.curList.listId} items={items} setItems={setItems}/>
        </ToDoListStyled>
    )
}

const useToDoList = (curList, setItems) => {
    useEffect(() => {
            HttpService.getClient().get(HttpService.getEndpoint("getItems"), {params: {listId: curList.listId}}) // modify
                .then(response => response.data.sort((a, b) => a.itemIndex - b.itemIndex))
                .then(items => setItems(items))
                .catch(e => {
                    setItems([])
                    console.info("To Do List Fetch Failure")
                })
        },
        [setItems, curList]
    );
}

export {ToDoList, useToDoList}