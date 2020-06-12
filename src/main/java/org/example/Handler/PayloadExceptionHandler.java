package org.example.Handler;

import org.example.Exception.PayloadException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PayloadExceptionHandler implements ExceptionMapper<PayloadException> {
    @Override
    public Response toResponse(PayloadException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
