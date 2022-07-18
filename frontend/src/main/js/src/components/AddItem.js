import {AddButton, TextArea} from "./styles/AddItem.styled";
import {DiscardButton, EditCard, BackButton, SubmitButton} from "./EditCard";
import {useEffect, useState} from "react";


// rem to do conditioonal formatting on To Do list to not display when no list is selected
const AddItem = ({listId, items, setItems}) => {
    const [cardVisible, setCardVisible] = useState(false)
    useCardVisible({setCardVisible, listId})
    if (cardVisible) {
        return <EditCard items={items}
                         setItems={setItems}
                         listId={listId}
                         itemIndex={items.length}
                         setCardVisible={setCardVisible}
        />
    }
    return <AddButton onClick={handleAdd({setCardVisible})}>{"+"}</AddButton>
}

const handleAdd = ({setCardVisible}) => (event) => {
    event.preventDefault()
    setCardVisible(true)
}

const useCardVisible = ({setCardVisible, listId}) => {
    useEffect( () => setCardVisible(false), [listId])
}

export {AddItem}
