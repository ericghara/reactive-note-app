import styled from "styled-components";
import {ToDoCardStyled} from "./ToDoListContainer.styled";
import {Icon} from "./SelectLists.styled";
import {Colors} from "./theme";

const TextArea = styled.textarea`
    font-size: inherit;
    font: inherit;
    overflow-y: scroll;
    overflow-x: clip;
    outline: none;
    resize: none;
    width: 100%;
    border: .5px solid ${Colors["dark-border"]};
`

const ButtonNav = styled.nav`
    display: flex;
    flex-direction: row;
    width: 100%;
    justify-content: flex-end;
`
const EditIcon = styled(Icon)`
    margin: 0 0px 0 10px;
    padding: 1px 10px 1px 10px;
    border-radius: 4px;
    width: 40px;
    border: 1px solid ${Colors["dark-border"]};
`

export {TextArea, ButtonNav, EditIcon}