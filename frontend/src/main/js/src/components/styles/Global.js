import { createGlobalStyle } from "styled-components";
import {Colors} from "./theme";

const GlobalStyles = createGlobalStyle`
  * {
    box-sizing: border-box;
  }
  
  body {
    background: ${Colors["bkg-main"]};
    color: ${Colors["text-normal"]};
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
  
  footer {
    text-align: center;
    font-size: .66em;
    color: ${Colors["light-border"]};
    position: absolute;
    width: 100%;
    padding-bottom: 15px;
  }
`

export default GlobalStyles;