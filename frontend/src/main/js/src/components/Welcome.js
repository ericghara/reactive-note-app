import {
  ButtonsHolderStyled,
  ListButtonStyled,
  SelectedListContainer,
  SelectListsStyled
} from "./styles/SelectLists.styled";
import {ToDoListStyled} from "./styles/ToDoListContainer.styled";

const Welcome = () =>
  <SelectListsStyled>
    <ButtonsHolderStyled>
    <ListButtonStyled>a</ListButtonStyled>
    <ListButtonStyled>b</ListButtonStyled>
    </ButtonsHolderStyled>
    <ToDoListStyled></ToDoListStyled>
  </SelectListsStyled>

export { Welcome }