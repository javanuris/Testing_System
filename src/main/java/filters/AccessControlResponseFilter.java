package filters;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class AccessControlResponseFilter implements ContainerResponseFilter{
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerRequestContext.setMethod("GET");

        containerRequestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        containerRequestContext.getHeaders().add("Access-Control-Allow-Headers","Authorization");
        containerRequestContext.getHeaders().add("Access-Control-Allow-Headers","Authorization");

        String override = containerRequestContext.getHeaders().getFirst( "X-HTTP-Method-Override");
        if (override != null) {
            containerRequestContext.setMethod(override);
        }
    }
}
