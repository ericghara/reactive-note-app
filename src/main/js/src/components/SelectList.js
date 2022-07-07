import {useEffect, useState} from "react"
import HttpService from "../services/HttpService"
import {
    ButtonsHolderStyled,
    Icon,
    ListButtonStyled,
    SelectedListDivStyled,
    SelectListsStyled
} from "./styles/SelectLists.styled"
import {CurListHeader} from "./ToDoListHeader";
import UserService from "../services/UserService";
import {IconService} from "../services/IconService";
import {deepCopy} from "../services/Common";

//state props: {availLists, setAvailLists, curList, setCurList}
const SelectList = (stateProps) => {
    const {availLists, setAvailLists, curList} = stateProps;
    useFetchLists(setAvailLists, availLists)
    return (
        <>
        <SelectListsStyled>
            <ButtonsHolderStyled>
                <ListButtons availLists={availLists} stateProps={stateProps} />
                <AddListButton availLists={availLists} setAvailLists={setAvailLists}/>
            </ButtonsHolderStyled>
        </SelectListsStyled>
        </>
    )
}

const ListButton = ({listId, listName, userId, selected, stateProps}) => {
    return <ListButtonStyled onClick={handleListClick({listId, listName, userId}, stateProps)} selected={selected}
                             width={150}>{listName}</ListButtonStyled>
}

const AddListButton = ({availLists, setAvailLists}) =>
    <ListButtonStyled onClick={handleAddList(availLists, setAvailLists)}
                      selected={false}
                      width={50}>{"+"}</ListButtonStyled>

const SelectedListNav = ({listId, listName, userId, selected, stateProps}) => {
    return (<SelectedListDivStyled>
            <div>{listName}</div>
            <Icon src={IconService.get("trash")} onDoubleClick={handleDeleteList(listId, stateProps)} alt={"delete icon"}/>
        </SelectedListDivStyled>)
}


const handleListClick = (clickedList, {setCurList, curList, availLists, setAvailLists}) => (event) => {
    event.preventDefault()
    const updatedLists = availLists.map(lprops => Object.assign({}, lprops))
        .map(lprops => {
            const {listId, selected} = lprops;
            lprops.selected = listId === clickedList.listId;
            return lprops
        });
    setAvailLists(updatedLists)
    setCurList(clickedList)
}


const handleAddList = (availLists, setAvailLists) => (event) => {
    event.preventDefault()
    HttpService.getClient().post(HttpService.getEndpoint("postList"), {listName: ("untitled "  + availLists.length), userId: UserService.getUserId() },)
        .then(({data}) => {
            console.log(data)
            const newList = deepCopy(availLists);
            newList.push(data)
            setAvailLists(newList)
        })
        .catch(e => console.error("Couldn't add a list"))
}

const handleDeleteList = (listId, {availLists, setAvailLists, setCurList}) => (event) => {
    event.preventDefault()
    HttpService.getClient().delete(HttpService.getEndpoint("deleteList") )
        .then( ({data}) => {
            console.log(data)
            const newList = deepCopy(availLists)
            setAvailLists(newList.filter(l => l.listId !== listId))
            setCurList({listName: ''})
        })
        .catch( e => alert("Unable to delete.  Please refresh the page."))
}

const useFetchLists = (setAvailLists, availLists) => {
    useEffect(() => {
            HttpService.getClient().get(HttpService.getEndpoint("getLists"), {params: {userId: 1}})
                .then(({data}) => setAvailLists(data))
                .catch(e => {
                    setAvailLists([])
                })
        }
        , [setAvailLists]
    )
}



const ListButtons = ({availLists, stateProps}) =>
    availLists.map(({listId, listName, userId, selected}) => {
        return selected === true ?
            <SelectedListNav listId={listId}
                        listName={listName}
                        userId={userId}
                        selected={selected}
                        stateProps={stateProps}
                        key={listId}/>

    : <ListButton listId={listId}
                           listName={listName}
                           userId={userId}
                           selected={selected}
                           stateProps={stateProps}
                           key={listId}/>
    })

export {SelectList}