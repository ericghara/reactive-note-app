import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
  * {
    box-sizing: border-box;
  }

  body {
    background: #ebfbff;
    color: hsl(192, 100%, 9%);
    font-family: 'Roboto', sans-serif;
    margin: 0;
    font-size: 1.3em;
  }

  pre {
    font-family: 'Roboto', sans-serif;
  }
  
  img {
    max-width: 100%;
  }
`

export default GlobalStyles;