import styled from "styled-components"
import {Icon} from "./SelectLists.styled";

const ToDoListStyled = styled.div`
    display: flex;
    align-items: center;
    flex-direction: column;
    width: 700px;
    background-color: #ebfbff;
    max-width: 100%;
    min-width: 25%;
    padding: 10px 20px;
    margin: auto auto;
`

const ToDoCardStyled = styled.div`
    background-color: #fff;
    border-radius: 7px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    width: 100%;
    margin: 40px 0;
    padding: 60px;
`
const ToDoListHeaderStyled = styled.h2`
    display: block;
    min-height: 50px;
    text-align: center;
    width: fit-content;
    margin-left: 25px;
`
const HeaderIcon = styled(Icon)`
    margin-left: 7px;
`

export {ToDoListStyled, ToDoCardStyled, ToDoListHeaderStyled, HeaderIcon};