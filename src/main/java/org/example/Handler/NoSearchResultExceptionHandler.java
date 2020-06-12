package org.example.Handler;

import org.example.Exception.NoSearchResultException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSearchResultExceptionHandler implements ExceptionMapper<NoSearchResultException> {

    @Override
    public Response toResponse(NoSearchResultException e) {
        return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
    }
}
