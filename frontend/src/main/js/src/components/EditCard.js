import {ButtonNav, EditCardStyled, EditIcon, TextArea} from "./styles/EditCard.styled";
import {Icon} from "./styles/SelectLists.styled";
import {IconService} from "../services/IconService";
import {useEffect, useState} from "react";
import HttpService from "../services/HttpService";
import {ToDoCardStyled} from "./styles/ToDoItem.styled";

const EditCard = ({items, setItems, listId, itemIndex, setCardVisible}) => {
    const [editItem, setEditItem] = useState( initialItemState({items,itemIndex,listId}) )
    useEditCard({setEditItem, items, itemIndex, listId})
    return <>
    <ToDoCardStyled>
    <InputBox setEditItem={setEditItem} editItem={editItem}/>
        <ButtonNav>
            <BackButton setCardVisible={setCardVisible}/>
            <SubmitButton setCardVisible={setCardVisible} editItem={editItem} items={items} setItems={setItems}/>
        </ButtonNav>
    </ToDoCardStyled>
    </>
}

const InputBox = ({setEditItem, editItem}) =>
    <TextArea value={editItem.itemText}
              onChange={ (event) => handleInputBoxChange({event, setEditItem, editItem})}
              rows={8}
              placeholder={"Note to self..."}/>

const BackButton = ({setCardVisible}) =>
    <EditIcon src={IconService.get('undo')} onClick = {handleBack({setCardVisible})} alt={'reset'}/>

const SubmitButton = ({setCardVisible,editItem, items, setItems}) =>
    <EditIcon src={IconService.get('send')}
              onClick={handleSubmit({setCardVisible, editItem, items, setItems})}
              alt={'submit'}/>


const initialItemState = ({items,itemIndex,listId}) => {
    if (itemIndex === items.length) {
        return nextItem({listId,itemIndex})
    }
    return {...items.find(i => itemIndex === i.itemIndex)}
}

const nextItem = ({listId, itemIndex}) => ({
    itemId : null,
    listId : listId,
    itemIndex : itemIndex,
    itemText : ""
})

const handleInputBoxChange = ({event, setEditItem, editItem}) => {
    const newEditItem = {...editItem}
    newEditItem.itemText = event.target.value
    setEditItem(newEditItem)
}

const handleSubmit = ({setCardVisible, editItem, items, setItems})  => (event) => {
    event.preventDefault()
    if (editItem.itemId == null) {
        console.log("Posting a new item")
        HttpService.getClient().post(HttpService.getEndpoint('postItem'), editItem)
            .then(({data}) => {
                setItems([...items, data])
            })
            .catch(e => console.error("Couldn't add an item"))
    }
    else {
        console.log("Putting an existing item")
        HttpService.getClient().put(HttpService.getEndpoint('putItem'), editItem)
            .then(({data}) => {
                let updateItems = [...items]
                updateItems.filter(i => data.itemId === i.itemId).forEach(i => Object.assign(i, data))
                setItems(updateItems)
                setCardVisible(false)
            })
            .catch(e => console.error("Couldn't add an item") )
    }
}

const handleBack = ({setCardVisible}) => (event) => {
    event.preventDefault()
    setCardVisible(false)
}

const useEditCard = ({setEditItem, items, itemIndex, listId}) => {
    useEffect( () => {
        if (itemIndex === items.length) { // i.e. add new item vs edit
            setEditItem(initialItemState({items,itemIndex,listId}) )
        }
    }, [items] )
}

export {EditCard}