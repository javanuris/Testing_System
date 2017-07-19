package filters;

import entity.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by User on 19.07.2017.
 */
public class SecurityContextImpl implements SecurityContext{
    private User user;
    private Principal principal;

    public SecurityContextImpl(final User user){
        this.user = user;
        this.principal = new Principal() {
            @Override
            public String getName() {
                return user.getPhone();
            }
        };
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return (role.equals(user.getRole().getName()));
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
