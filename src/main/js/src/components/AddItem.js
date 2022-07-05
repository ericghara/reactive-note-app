import {AddButton, TextArea} from "./styles/AddItem.styled";


// rem to do conditioonal formatting on To Do list to not display when no list is selected
const AddItem = (itemProps) => {
    return (
        <>
        <AddButton>{"+"}</AddButton>
            <TextArea rows={8} placeholder={"Note to self..."}/>
        </>)
}



export {AddItem}
