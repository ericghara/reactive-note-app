import styled from "styled-components";
import {Colors} from "./theme";

const AddButton = styled.button`
  text-align: center;
  border-radius: 8px;
  width: 150px;
  font-weight: 700;
  font-size: inherit;
  border: 2px solid ${Colors["selected-border"]};
  background-color: ${Colors["fill-selected"]};
  color: ${Colors["text-normal"]};
  max-width: 100%;
  min-width: 25%;
  padding: 10px 20px;
  margin: auto auto;
  cursor: pointer;

  &:hover {
    opacity: .93;
    box-shadow: 0 0 5px ${Colors["shadow-dark"]};
  }
`

export {AddButton};