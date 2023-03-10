import styled from 'styled-components';
import {Colors} from "./theme";

export const StyledHeader = styled.header`
  background-color: ${Colors["bkg-contrast"]};
  padding: 5px 0;
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
  color: ${Colors["text-selected"]};
  border-bottom: 2px solid ${Colors["dark-border"]};
`

export const Nav = styled.nav`
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin: 5px 15px 5px 15px;
`
export const Button = styled.button`
  border-radius: 50px;
  border: 2px solid ${Colors["selected-border"]};
  box-shadow: 0 0 10px ${Colors["shadow-light"]};
  cursor: pointer;
  font-weight: 700;
  font-size: inherit;
  padding: 15px 45px;
  background-color: ${Colors["fill-selected"]};
  color: ${Colors["text-normal"]};

  &:hover {
    opacity: 0.93;
    box-shadow: 0 0 5px ${Colors["shadow-dark"]};
  }
`