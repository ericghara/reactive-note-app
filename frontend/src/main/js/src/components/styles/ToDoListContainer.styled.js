import styled from "styled-components"
import {Icon} from "./SelectLists.styled";
import {Colors} from "./theme";

const ToDoListStyled = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  background-color: ${Colors["bkg-main"]};
  padding: 10px 20px;
  margin: auto auto 200px auto;
  max-width: 800px;
  min-height: 100%;
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
  border: solid ${Colors["dark-border"]};
  border-width: 0 0 2px 0;
  color: ${Colors["text-selected"]};
  margin-left: 25px;
  font: inherit;
  background-color: ${Colors["bkg-main"]};
  &:focus {
    outline: none;
    border: solid ${Colors["text-selected"]};
    border-width: 0 0 2px 0;
  }
`

const HeaderIcon = styled(Icon)`
  margin-left: 7px;
`

export {ToDoListStyled, ToDoListHeaderStyled, HeaderIcon, ToDoListHeaderInput};