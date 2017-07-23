package filters;

import entity.User;
import services.UserService;
import utils.Secured;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        User user = null;
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Basic".length()).trim();

        try {
            user = validateToken(token, requestContext);
            requestContext.setSecurityContext(new SecurityContextImpl(user));
        } catch (Exception e) {
            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User con not accsses to the this resources filter").build();
            requestContext.abortWith(unauthorizedStatus);
        }
    }

    private User validateToken(String token,ContainerRequestContext requestContext) throws Exception {
        UserService userService = new UserService();
        User user = userService.findUserByToken(token);
        if (user == null) {
            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User con not accsses to the this resources validate").build();
            requestContext.abortWith(unauthorizedStatus);
        }
        return user;
    }
}

