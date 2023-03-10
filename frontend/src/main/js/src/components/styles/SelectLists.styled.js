import styled from "styled-components"
import {mobileMediaPx, Colors} from "./theme";

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
    background-color: ${Colors["bkg-main"]};
  }
  margin: 90px 0px;
  background-color: ${Colors["bkg-main"]};
  max-height: 100%;


  &::-webkit-scrollbar {
    width: 5px;
    height: 5px;
  }

  &::-webkit-scrollbar-track {
    box-shadow: inset 0px 0px 2px ${Colors["shadow-dark"]};
  }

  &::-webkit-scrollbar-thumb {
    background-color: ${Colors["light-border"]};
    outline: 1px solid ${Colors["light-border"]};
    border-radius: 5px;
  }
`
const ListButtonStyled = styled.button`
  display: block;
  border-radius: 0 13px 13px 0;
  border: 2px solid ${Colors["border-button-normal"]};
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
  &:hover {
    box-shadow: 0 0 10px ${Colors["shadow-med"]};
    border-color: ${Colors["light-border"]};
    transform: scale(1.01);
  }
  margin: 10px 0;
  background-color: ${Colors["fill-normal"]};
  color: ${Colors["text-selected"]};

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
  border: 2px solid ${Colors["light-border"]};
  background-color: ${Colors["fill-selected"]};
  color: ${Colors["text-normal"]};
  cursor: default;
  border-radius: 0 13px 13px 0;
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