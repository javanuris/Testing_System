package filters;

import entity.Role;
import entity.User;
import org.glassfish.jersey.internal.util.Base64;
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
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        requestContext.setSecurityContext(new SecurityContextImpl(null));


        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("User con not accsses to the this resources").build();
        requestContext.abortWith(unauthorizedStatus);
    }

    private void validateToken(String token) throws Exception {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    }
}

