import {useEffect} from "react"
import HttpService from "../services/HttpService"
import {
    ButtonsHolderStyled,
    Icon,
    ListButtonStyled,
    SelectedListContainer,
    SelectedListText,
    SelectListsStyled
} from "./styles/SelectLists.styled"
import UserService from "../services/UserService";
import {IconService} from "../services/IconService";
import {deepCopy} from "../services/Common";
import RenderOnAuthenticated from "./RenderOnAuthenticated";

//state props: {availLists, setAvailLists, curList, setCurList}
const SelectList = (stateProps) => {
    const {availLists, setAvailLists} = stateProps;
    useFetchLists(stateProps)
    return (
        <>
            <SelectListsStyled>
                <ButtonsHolderStyled>
                    <ListButtons availLists={availLists} stateProps={stateProps}/>
                    <RenderOnAuthenticated>
                        <AddListButton availLists={availLists} setAvailLists={setAvailLists}/>
                    </RenderOnAuthenticated>
                </ButtonsHolderStyled>
            </SelectListsStyled>
        </>
    )
}

const ListButton = ({listId, listName, userId, stateProps}) => {
    return <ListButtonStyled onClick={handleListClick({listId, listName, userId}, stateProps)}
                             width={150}>{listName}</ListButtonStyled>
}

const AddListButton = ({availLists, setAvailLists}) =>
    <ListButtonStyled onClick={handleAddList(availLists, setAvailLists)}
                      width={50}>{"+"}</ListButtonStyled>

const SelectedListNav = ({listId, listName, userId, stateProps}) => {
    return (<SelectedListContainer>
        <SelectedListText>{listName}</SelectedListText>
        <RenderOnAuthenticated>
            <Icon src={IconService.get("trash")} onClick={handleDeleteList(listId, stateProps)} alt={"delete icon"}/>
        </RenderOnAuthenticated>
    </SelectedListContainer>)
}


const handleListClick = (clickedList, {setCurList}) => (event) => {
    event?.preventDefault()
    setCurList(clickedList)
}


const handleAddList = (availLists, setAvailLists) => (event) => {
    event.preventDefault()
    HttpService.getClient().post(HttpService.getEndpoint("postList"), {
        listName: ("untitled " + availLists.length),
        userId: UserService.getUserId()
    },)
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
    const toDelete = availLists.find(l => l.listId === listId)
    HttpService.getClient().delete(HttpService.getEndpoint("deleteList"), {data: toDelete})
        .then(({data}) => {
            console.log(data)
            const newList = deepCopy(availLists)
            setAvailLists(newList.filter(l => l.listId !== listId))
            setCurList({listName: ''})
        })
        .catch(e => alert("Unable to delete.  Please refresh the page."))
}

const useFetchLists = ({setAvailLists, curList, setCurList}) => {
    useEffect(() => {
            HttpService.getClient().get(HttpService.getEndpoint("getLists"))
                .then(({data}) => {
                    data.sort((a, b) => a.listId > b.listId ? 1 : a.listId < b.listId ? -1 : 0)
                    setInitialList({curList, data, setCurList})
                    setAvailLists(data)
                })
                .catch(e => {
                    setAvailLists([])
                    console.info(e)
                })
        }
        , [setCurList]
    )
}

const setInitialList = ({curList, data, setCurList}) => {
    if (Object.keys(curList)?.length === 0 && data?.length) {
        setCurList(data[0])
    }
}

const ListButtons = ({availLists, stateProps}) => {
    const curList = stateProps.curList
    return availLists.map(({listId, listName, userId}) => {
        return curList.listId === listId ?
            <SelectedListNav listId={listId}
                             listName={listName}
                             userId={userId}
                             stateProps={stateProps}
                             key={listId}/>

            : <ListButton listId={listId}
                          listName={listName}
                          userId={userId}
                          stateProps={stateProps}
                          key={listId}/>
    })
}

export {SelectList}