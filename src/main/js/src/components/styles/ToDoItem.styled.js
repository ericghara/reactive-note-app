import styled from "styled-components";
import {ButtonNav} from "./EditCard.styled";
import {Icon} from "./SelectLists.styled";

const ToDoCardStyled = styled.pre`
    background-color: #fff;
    border-radius: 7px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    width: 100%;
    white-space: pre-wrap;
    word-wrap: break-word;
    margin: 20px 0;
    padding: 20px 30px 10px 30px;
`

const ToDoCardIcon = styled(Icon)`
    margin: 0 0 5px 7px;
`

const ToDoCardNav = styled(ButtonNav)`
`

export {ToDoCardStyled, ToDoCardIcon, ToDoCardNav}