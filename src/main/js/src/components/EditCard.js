import {ButtonNav, EditCardStyled, EditIcon, TextArea} from "./styles/EditCard.styled";
import {Icon} from "./styles/SelectLists.styled";
import {IconService} from "../services/IconService";
import {useEffect, useState} from "react";
import HttpService from "../services/HttpService";

const EditCard = ({items, setItems, listId, itemIndex, setCardVisible}) => {
    const [editItem, setEditItem] = useState( initialItemState({items,itemIndex,listId}) )
    useEditCard({setEditItem, items, itemIndex, listId})
    return <>
    <EditCardStyled>
    <InputBox setEditItem={setEditItem} editItem={editItem}/>
        <ButtonNav>
            <BackButton setCardVisible={setCardVisible}/>
            <DiscardButton/>
            <SubmitButton editItem={editItem} items={items} setItems={setItems}/>
        </ButtonNav>
    </EditCardStyled>
    </>
}

const InputBox = ({setEditItem, editItem}) =>
    <TextArea value={editItem.itemText}
              onChange={ (event) => handleInputBoxChange({event, setEditItem, editItem})}
              rows={8}
              placeholder={"Note to self..."}/>

const BackButton = ({setCardVisible}) =>
    <EditIcon src={IconService.get('undo')} onClick = {handleBack({setCardVisible})} alt={'reset'}/>


const DiscardButton = () =>
    <EditIcon src={IconService.get('trash')} alt={'trash'}/>

const SubmitButton = ({editItem, items, setItems}) =>
    <EditIcon src={IconService.get('send')}
              onClick={handleSubmit({editItem, items, setItems})}
              alt={'submit'}/>


const initialItemState = ({items,itemIndex,listId}) => {
    if (itemIndex === items.length) {
        return nextItem({listId,itemIndex})
    }
    return {...items.find(i => listId === i.listId)}
}

const nextItem = ({listId, itemIndex}) => ({
    itemId : null,
    listId : listId,
    itemIndex : itemIndex,
    itemText : ""
})

const handleInputBoxChange = ({event, setEditItem, editItem}) => {
    console.log(event.target.value)
    const newEditItem = {...editItem}
    newEditItem.itemText = event.target.value
    setEditItem(newEditItem)
}

const handleSubmit = ({editItem, items, setItems})  => (event) => {
    event.preventDefault()
    console.log(editItem)
    if (editItem.itemId == null) {
        console.log("Posting a new item")
        HttpService.getClient().post(HttpService.getEndpoint('postItem'), editItem)
            .then(({data}) => {
                setItems([...items, data])
            })
            .catch(e => console.error("Couldn't add an item"))
    }
}

const handleBack = ({setCardVisible}) => (event) => {
    event.preventDefault()
    setCardVisible(false)
}

const useEditCard = ({setEditItem, items, itemIndex, listId}) => {
    useEffect( () => {
        console.log(`useEditCard ${listId}`)
        setEditItem(initialItemState({items,itemIndex,listId}) ) }, [items] )
}

export {EditCard, BackButton, DiscardButton, SubmitButton}