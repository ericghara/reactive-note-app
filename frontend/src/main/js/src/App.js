import {useState} from 'react'
import {ToDoList} from './components/ToDoList';
import {SelectList} from './components/SelectList';
import {Header} from './components/Header'
import GlobalStyles from './components/styles/Global';
import {AppContainer} from "./components/styles/AppContainer.styled";

const App = () => {
    const [items, setItems] = useState([])
    const [curList, setCurList] = useState({})
    const [availLists, setAvailLists] = useState([])

    return (
        <>
            <GlobalStyles/>
            <Header/>
            <AppContainer>
                <SelectList availLists={availLists} setAvailLists={setAvailLists} curList={curList}
                            setCurList={setCurList}/>
                <ToDoList listProps={{curList, setCurList, availLists, setAvailLists}} items={items} setItems={setItems}/>
            </AppContainer>
            <footer>
                {`© Copyright 2022 Eric Gharakhanian`}
            </footer>
        </>
    )
}

export default App;
