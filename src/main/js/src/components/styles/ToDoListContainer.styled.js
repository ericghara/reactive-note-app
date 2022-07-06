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
  margin: auto auto 200px auto;
`

const ToDoListHeaderStyled = styled.h2`
  display: block;
  min-height: 50px;
  text-align: center;
  width: fit-content;
  margin-left: 25px;
`

const ToDoListHeaderInput = styled.input`
  text-align: center;
  border: ridge #c7c7c7;
  border-width: 0 0 2px 0;
  color: inherit;
  //border-radius: 7px;
  margin-left: 25px;
  font: inherit;
  background-color: #ebfbff;
  &:focus {
    outline: none;
    border: ridge hsl(192,100%,9%);
    border-width: 0 0 2px 0;
  }
`

const HeaderIcon = styled(Icon)`
  margin-left: 7px;
`

export {ToDoListStyled, ToDoListHeaderStyled, HeaderIcon, ToDoListHeaderInput};