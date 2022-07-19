import {useState} from "react";
import {IconService} from "../services/IconService";
import {ToDoCardIcon, ToDoCardNav, ToDoCardStyled} from "./styles/ToDoItem.styled";
import {EditCard} from "./EditCard";
import HttpService from "../services/HttpService";
import UserService from "../services/UserService";
import ReactMarkdown from "react-markdown";


const ToDoItem = ({itemId, items, setItems, listId, itemIndex, itemText}) => {
    const [editVisible, setEditVisible] = useState(false)
    // useEffect(() => setEditVisible(false), [items]) // change this to only collapse currently submitted item
    if (editVisible) {
        return <EditCard setCardVisible={setEditVisible} items={items} setItems={setItems} itemIndex={itemIndex}
                         listId={listId}/>
    }
    return <ToDoCardStyled>
        <ReactMarkdown>
            {itemText}
        </ReactMarkdown>
        <ToDoCardNav children={getButtons({editVisible, setEditVisible, items, itemId, setItems})}/>
    </ToDoCardStyled>
}

const getButtons = ({editVisible, setEditVisible, items, itemId, setItems}) => {
    if (editVisible || !UserService.isLoggedIn()) {
        return null
    }
    return <>
        <ToDoCardIcon src={IconService.get('trash')}
                      onClick={handleDelete({items, itemId, setItems})}
                      alt={'trash'}/>
        <ToDoCardIcon src={IconService.get('edit')}
                      onClick={handleToggleVisible({editVisible, setEditVisible})}
                      alt={'edit'}/>
    </>
}

const handleToggleVisible = ({editVisible, setEditVisible}) => (event) => {
    event.preventDefault()
    console.log('toggle', editVisible)
    setEditVisible(!editVisible)
}

const handleDelete = ({items, itemId, setItems}) => (event) => {
    event.preventDefault()
    const delItem = items.find(i => i.itemId === itemId)
    HttpService.getClient().delete(HttpService.getEndpoint('deleteItem'), {data: delItem})
        .then(() => {
            const updItems = items.filter(i => i.itemId !== itemId)
            updItems.forEach(i => i.itemIndex > delItem.itemIndex && i.itemIndex--)
            setItems(updItems)
        })
        .catch(e => console.warn('Unable to delete an item'))
}

export {ToDoItem}