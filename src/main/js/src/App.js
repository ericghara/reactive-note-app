import { useState } from 'react'
import { ToDoList } from './components/ToDoList'; 
import { SelectList } from './components/SelectList'; 
import { Header } from './components/Header'
import GlobalStyles from './components/styles/Global'; 

const App = () => {
  const [items, setItems] = useState([])
  const [curList, setCurList] = useState([])
  const [availLists, setAvailLists] = useState([])

  return (
    <>
    <GlobalStyles />
    <Header/>
    <div>
        <SelectList availLists={availLists} setAvailLists={setAvailLists} curList={curList} setCurList={setCurList} />
        <ToDoList list={curList} items={items} setItems={setItems} />
    </div>
    </>
  )
}

export default App;
