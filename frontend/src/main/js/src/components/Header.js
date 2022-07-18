import {Nav, Button, StyledHeader, GreeterDiv} from './styles/Header.styled.js'
import  UserService from '../services/UserService'
import RenderOnAnonomous from "./RenderOnAnonomous";
import RenderOnAuthenticated from "./RenderOnAuthenticated";

const Header = () => (
    <StyledHeader>
        <Nav>
            <Greeter/>
            <RenderOnAnonomous>
                <Button onClick={() => UserService.doLogin() } >Login</Button>
            </RenderOnAnonomous>
            <RenderOnAuthenticated>
                <Button onClick={() => UserService.doLogout()}>Logout</Button>
            </RenderOnAuthenticated>
        </Nav>
    </StyledHeader>
    )

const Greeter = () =>
    <div>{
        "Hello, " +
        (UserService.isLoggedIn()
            ? capitalizeFirstLetter( UserService.getUsername() )
            : "please login")}
    </div>

const capitalizeFirstLetter = (str) => {
    return str[0].toUpperCase() + str.slice(1)
}


export { Header }

