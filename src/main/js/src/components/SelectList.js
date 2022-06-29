import {useEffect} from "react"
import HttpService from "../services/HttpService"
import { ListButtonStyled, SelectListsStyled, ButtonsHolderStyled, SelectListsHeaderStyled } from "./styles/SelectLists.styled"

//state props: {availLists, setAvailLists, curList, setCurList}
const SelectList = (stateProps) => {
    var {availLists, setAvailLists} = stateProps
    useFetchLists(setAvailLists, availLists)
    return (
        <SelectListsStyled>
            <SelectListsHeaderStyled/>
            <ButtonsHolderStyled>
                {
                availLists.map( ({listId, listName, userId, selected}) => 
                {
                return <ListButton listId={listId} 
                                listName={listName} 
                                userId={userId}
                                selected={selected} 
                                stateProps={stateProps}
                                key={listId} />
                })
                }
            </ButtonsHolderStyled>
        </SelectListsStyled>
    )
}

const ListButton = ({listId, listName, userId, selected, stateProps}) => {
    return <ListButtonStyled onClick={handleListClick({listId, listName, userId}, stateProps)} selected={selected}>{listName}</ListButtonStyled>
}

const handleListClick = (clickedList, {setCurList, curList, availLists, setAvailLists}) => (event) => {
    event.preventDefault()
    var updatedLists = availLists.map( lprops => Object.assign({}, lprops) )
                                .map( lprops => {
                                    var {listId, selected} = lprops
                                    if (listId === clickedList.listId) {
                                        lprops.selected = true
                                    }
                                    else {
                                        lprops.selected = false
                                    }
                                    return lprops
                                })
    setAvailLists(updatedLists)                                
    setCurList(clickedList)
}

const useFetchLists = (setAvailLists, availLists) => {
    useEffect( () => {
                HttpService.getClient().get(HttpService.getEndpoint("getLists"), {params : {userId : 1}})
                    .then( ({data}) => setAvailLists(data) ) 
                    .catch(e => { 
                        console.log(e)
                        setAvailLists([]) } ) 
                }
    , [setAvailLists]
    )
}

export {SelectList}