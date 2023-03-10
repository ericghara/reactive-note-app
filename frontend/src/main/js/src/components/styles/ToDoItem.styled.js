import styled from "styled-components";
import {ButtonNav} from "./EditCard.styled";
import {Icon} from "./SelectLists.styled";
import {Colors} from "./theme";

const ToDoCardStyled = styled.div`
  background-color: ${Colors["bkg-contrast"]};
  border-radius: 7px;
  border: 1px solid ${Colors["card-border"]};
  box-shadow: 0 0 5px ${Colors["shadow-light"]};
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
    border-left: 4px solid ${Colors["dark-border"]};
    padding-left: 4px;
    color: ${Colors["text-normal"]};
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