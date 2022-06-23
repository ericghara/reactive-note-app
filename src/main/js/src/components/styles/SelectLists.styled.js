import styled from "styled-components"

const SelectListsStyled = styled.div`
    float: left;
    flex: 1;
    margin: 0px 0px;
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
    padding: 15px 60px;
    margin: 10px 0;
    background-color: ${({selected}) => selected === true ? "#fff3c7" : "#f8c1ff80"};
    color: ${({selected}) => selected === true ? "#545454" : "#4c3f3f"};

    &:hover {
        opacity: 0.9;
        transform: scale(0.98);
      }
`
const ButtonsHolderStyled = styled.div` 
    margin: 0px 10px 0px 0px;
    height: 100%;
`

const SelectListsHeaderStyled = styled.h2`
    text-align: center;
    height: 50px;
`

export { SelectListsStyled, ListButtonStyled, ButtonsHolderStyled, SelectListsHeaderStyled }