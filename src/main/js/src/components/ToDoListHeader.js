import {HeaderIcon, ToDoListHeaderInput, ToDoListHeaderStyled} from "./styles/ToDoListContainer.styled";
import {useEffect, useState} from "react";
import {IconService} from "../services/IconService";
import HttpService from "../services/HttpService";
import {deepCopy} from "../services/Common";

const CurListHeader = ({stateProps}) => {
    const {availLists, setAvailLists, curList} = stateProps;
    const [inputText, setInputText] = useState("")
    const [inputActive, setInputActive] = useState(false)
    const inputProps = {inputText, setInputText, inputActive, setInputActive}
    useCurListChange(stateProps, inputProps)
    if (curList?.listId === undefined) {
        return <ToDoListHeaderStyled/>
    }
    return (<ToDoListHeaderStyled>
        <EditActive stateProps={stateProps} inputProps={inputProps}/>
        <EditInactive stateProps={stateProps} inputProps={inputProps}/>

    </ToDoListHeaderStyled>)
}

const EditActive = ({stateProps, inputProps}) => {
    if (!inputProps.inputActive) {
        return null
    }
    return (
        <>
            <ToDoListHeaderInput value={inputProps.inputText}
                   onChange={handleInputChange(inputProps.setInputText)}/>
            <UndoIcon stateProps={stateProps}
                      inputProps={inputProps}/>
            <SaveIcon stateProps={stateProps}
                      inputProps={inputProps}/>
        </>
    )
}

const EditInactive = ({stateProps, inputProps}) => {
    if (inputProps.inputActive) {
        return null
    }
    return (
        <>
            <span>{stateProps.curList?.listName}</span>
            <EditIcon stateProps={stateProps} inputProps={inputProps}/>
        </>
    )
}

const EditIcon = ({stateProps, inputProps}) => {
    if (inputProps.inputActive) {
        return null
    }
    return <HeaderIcon onClick={handleEditListName(stateProps, inputProps)}
                       src={IconService.get("edit")}
                       alt={"edit"}
                       size={10}/>
}

const UndoIcon = ({stateProps, inputProps}) => {
    if (!inputProps.inputActive) {
        return null
    }
    return <HeaderIcon onClick={handleUndoEditListName(inputProps)}
                       src={IconService.get("undo")}
                       alt={"edit"}
                       size={10}/>
}

const SaveIcon = ({stateProps, inputProps}) => {
    if (!inputProps.inputActive) {
        return null
    }
    return <HeaderIcon onClick={handleSave(stateProps, inputProps)}
                       src={IconService.get("checkMark")}
                       alt={"edit"}
                       size={10}/>
}

const handleSave = ({availLists, setAvailLists, curList, setCurList}, {setInputActive, inputText}) => (event) => {
    event.preventDefault()
    const modifiedList = deepCopy(curList)
    modifiedList.listName = inputText
    HttpService.getClient().put(HttpService.getEndpoint("putList"), {...modifiedList})
        .then(({data}) => {
            const newLists = deepCopy(availLists);
            const newCurList = newLists.filter(l => data.listId === l.listId)
                .map(l => Object.assign(l, data))
            setAvailLists(newLists)
            setCurList(newCurList[0])
            setInputActive(false)
        })
        .catch(e => console.error("Couldn't add a list"))

}

const handleEditListName = ({curList = {listName: ""}}, {setInputActive, setInputText}) => (event) => {
    event.preventDefault()
    setInputText(curList.listName)
    setInputActive(true)
}

const handleUndoEditListName = ({setInputActive}) => event => {
    event.preventDefault()
    setInputActive(false)
}

const handleInputChange = (setInputText) => ({target}) => {
    setInputText(target.value)
}

const useCurListChange = ({curList}, {setInputActive}) => {
    useEffect(() => {
        setInputActive(false)
    }, [curList])
}

export {CurListHeader}