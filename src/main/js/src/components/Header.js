import { Nav, Button, StyledHeader } from './styles/Header.styled.js'
import  UserService from '../services/UserService'

const Header = () => (
    <StyledHeader>
    <Nav>
        <Button onClick={() => UserService.doLogin() } >Log In</Button>
    </Nav>
    </StyledHeader>
    )
    


export { Header }

