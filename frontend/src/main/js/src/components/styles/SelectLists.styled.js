import styled from "styled-components"
import {mobileMediaPx} from "./theme";

const SelectListsStyled = styled.div`
  float: left;
  @media (max-width: ${mobileMediaPx}) {
    float: none;
    display: flex;
    width: 100%;
    justify-content: flex-start;
    overflow-x: auto;
    overflow-y: hidden;
    white-space: nowrap;
    margin: 0 0;
    background-color: #ebfbff;
  }
  margin: 90px 0px;
  background-color: #ebfbff;
  max-height: 100%;


  &::-webkit-scrollbar {
    width: 5px;
    height: 5px;
  }

  &::-webkit-scrollbar-track {
    box-shadow: inset 0px 0px 2px rgba(0, 0, 0, 0.3);
  }

  &::-webkit-scrollbar-thumb {
    background-color: #c7c7c7;
    outline: 1px solid #d6cfe5;
    border-radius: 5px;
  }
`
const ListButtonStyled = styled.button`
  display: block;
  border-radius: 0px 13px 13px 0px;
  border: 2px solid ${({selected}) => selected === true ? "#c7c7c7" : "#d6cfe5"};
  cursor: pointer;
  font-weight: 600;
  font-size: inherit;
  padding: 15px 15px;
  width: ${({width}) => 1.5 * (width !== undefined ? width : 150)}px;
  @media (max-width: ${mobileMediaPx}) {
    border-radius: 30px;
    width: ${({width}) => width !== undefined ? width : 150}px;
    display: inline-block;
    overflow-x: hidden;
    text-overflow: ellipsis;
    vert-align: middle;
    margin: 10px 3px;
  }
  margin: 10px 0;
  background-color: ${({selected}) => selected === true ? "#fff3c7" : "#f8c1ff80"};
  color: ${({selected}) => selected === true ? "#545454" : "#4c3f3f"};

  &:hover {
    ${({selected}) => {
    if (selected === false) {
        return `opacity: 0.9;
                    transform: scale(0.98);`
    }
}}
  }
`
const SelectedListContainer = styled.div`
  display: flex;
  flex-direction: row;
  border: 2px solid #c7c7c7;
  background-color: #fff3c7;
  color: #545454;
  cursor: default;
  border-radius: 0px 13px 13px 0px;
  font-weight: 600;
  font-size: inherit;
  padding: 15px 15px;
  margin: 10px 0;
  width: ${({width}) => 1.5 * (width !== undefined ? width : 150)}px;
  @media (max-width: ${mobileMediaPx}) {
    width: auto;
    text-align: center;
    min-width: ${({width}) => 1 * (width !== undefined ? width : 150)}px;
    display: inline-flex;
    vertical-align: middle;
    border-radius: 30px;
    white-space: nowrap;
    margin: 10px 3px;
  }
`

const SelectedListText = styled.div`
  margin: auto;
  text-align: center;
`

const ButtonsHolderStyled = styled.div`
  margin: 0px 10px 0px 0px;
  height: 100%;
  @media (max-width: ${mobileMediaPx}) {
    margin: 0 auto;
  }
`

const SelectListsHeaderStyled = styled.h2`
  text-align: center;
  height: 50px;
`

const Icon = styled.img`
  width: ${({width}) => (width !== undefined ? width : 20)}px;
  margin-left: 7px;
  opacity: 0.75;

  &:hover {
    opacity: 0.95;
  }
`

export {
    SelectListsStyled,
    ListButtonStyled,
    ButtonsHolderStyled,
    SelectListsHeaderStyled,
    SelectedListContainer,
    Icon,
    SelectedListText
}