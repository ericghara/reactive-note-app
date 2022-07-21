import styled from 'styled-components'

export const StyledHeader = styled.header`
  background-color: #ffffff;
  padding: 5px 0;
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
  color: #4c3f3f;
  border-bottom: 2px solid #c7c7c7;
`

export const Nav = styled.nav`
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin: 5px 15px 5px 15px;
`
export const Button = styled.button`
  border-radius: 50px;
  border: 2px solid #d6cfe5dd;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  cursor: pointer;
  font-weight: 700;
  font-size: inherit;
  padding: 15px 45px;
  background-color: #fff3c7;
  color: #545454;

  &:hover {
    opacity: 0.95;
    transform: scale(0.99);
    box-shadow: 0 0 7px rgba(0, 0, 0, 0.15);
  }
`