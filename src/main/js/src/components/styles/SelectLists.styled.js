import styled from "styled-components"

const SelectListsStyled = styled.div`
  float: left;
  flex: 1;
  margin: 90px 0px;
  background-color: #ebfbff;
  max-height: 100%;
`
const ListButtonStyled = styled.button`
  display: block;
  border-radius: 0px 13px 13px 0px;
  border: 2px solid ${({selected}) => selected === true ? "#c7c7c7" : "#d6cfe5"};
  cursor: pointer;
  font-size: 16px;
  font-weight: 700;
  padding: 15px 15px;
  width: ${({width}) => width !== undefined ? width : 150}px;
  margin: 10px 0;
  background-color: ${({selected}) => selected === true ? "#fff3c7" : "#f8c1ff80"};
  color: ${({selected}) => selected === true ? "#545454" : "#4c3f3f"};

  &:hover {
    ${({selected}) => {
      if (selected === false) {
        return `opacity: 0.9;
                    transform: scale(0.98);`
        return ""
        }
        }}
  }
  `
  const SelectedListDivStyled = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    border: 2px solid #c7c7c7;
    background-color: #fff3c7;
    color: #545454;
    cursor: default;
    border-radius: 0px 13px 13px 0px;
    font-size: 16px;
    font-weight: 700;
    padding: 15px 15px;
    margin: 10px 0;
    width: ${({width}) => width !== undefined ? width : 150}px;
`

const ButtonsHolderStyled = styled.div`
  margin: 0px 10px 0px 0px;
  height: 100%;
`

const SelectListsHeaderStyled = styled.h2`
  text-align: center;
  height: 50px;
`

const Icon = styled.img`
  width: 20px;
  opacity: 0.75;
  &:hover {
    opacity: 0.95;
  }
`

export {SelectListsStyled, ListButtonStyled, ButtonsHolderStyled, SelectListsHeaderStyled, SelectedListDivStyled, Icon}