import styled from "styled-components"

const ToDoListStyled = styled.div`
    width: 700px;
    background-color: #ebfbff;
    max-width: 100%;
    min-width: 25%;
    padding: 10px 20px;
    margin: auto auto;
`

const ToDoCardStyled = styled.div`
    flex: 1;
    background-color: #fff;
    border-radius: 15px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    margin: 40px 0;
    padding: 60px;
`
const ToDoListHeaderStyled = styled.h2`
    display: block;
    text-align: center;
`

export { ToDoListStyled, ToDoCardStyled, ToDoListHeaderStyled }
