import {useState} from 'react'
import {ToDoList} from './components/ToDoList';
import {SelectList} from './components/SelectList';
import {Header} from './components/Header'
import GlobalStyles from './components/styles/Global';

const App = () => {
    const [items, setItems] = useState([])
    const [curList, setCurList] = useState({})
    const [availLists, setAvailLists] = useState([])

    return (
        <>
            <GlobalStyles/>
            <Header/>
                <SelectList availLists={availLists} setAvailLists={setAvailLists} curList={curList}
                            setCurList={setCurList}/>
                <ToDoList listProps={{curList, setCurList, availLists, setAvailLists}} items={items} setItems={setItems}/>
        </>
    )
}

export default App;
