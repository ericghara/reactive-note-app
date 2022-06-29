import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
    * {
    box-sizing: border-box;
    }

    body {
        background: #ebfbff;
        color: hsl(192, 100%, 9%);
        font-family: 'Roboto', sans-serif;
        font-size: 1.15em;
        margin: 0;
    }

    p {
        opacity: 0.6;
        line-height: 1.5;
    }

    img {
        max-width: 100%;
    }
`
export default GlobalStyles;