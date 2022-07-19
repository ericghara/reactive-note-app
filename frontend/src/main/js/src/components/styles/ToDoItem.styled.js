import styled from "styled-components";
import {ButtonNav} from "./EditCard.styled";
import {Icon} from "./SelectLists.styled";

const ToDoCardStyled = styled.div`
  background-color: #fff;
  border-radius: 7px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  width: 100%;
  white-space: pre-wrap;
  word-wrap: break-word;
  margin: 20px 0;
  padding: 20px 30px 10px 30px;
  h1, h2, h3, h4 {
    margin-block: .5em .5em;
    display: inline-block;
  }
  ul, ol, p, pre {
    margin-block: .25em .25em;
    display: inline-block;
  }
  blockquote {
    border-left: 4px solid #c7c7c7;
    padding-left: 4px;
    color: #545454;
    margin-block: 0.25em .25em;
    display: inline-block;
  }
`

const ToDoCardIcon = styled(Icon)`
  margin: 0 0 5px 7px;
`

const ToDoCardNav = styled(ButtonNav)`
`

export {ToDoCardStyled, ToDoCardIcon, ToDoCardNav}