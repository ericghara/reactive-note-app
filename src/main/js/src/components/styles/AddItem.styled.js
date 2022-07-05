import styled from "styled-components";

const AddButton = styled.button`
  text-align: center;
  border-radius: 8px;
  width: 200px;
  font-weight: 700;
  font-size: 16px;
  border: 2px solid #c7c7c7;
  background-color: #fff3c7;
  color: #545454;
  max-width: 100%;
  min-width: 25%;
  padding: 10px 20px;
  margin: auto auto;
  cursor: pointer;

  &:hover {
    opacity: 0.9;
    transform: scale(0.98);
  }
`

const TextArea = styled.textarea`
    overflow-y: scroll;
    overflow-x: clip;
    outline: none;
    width: 100%;
`
export {AddButton,TextArea};