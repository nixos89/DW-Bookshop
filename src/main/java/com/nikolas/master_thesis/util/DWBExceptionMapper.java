package com.nikolas.master_thesis.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class DWBExceptionMapper implements ExceptionMapper<DWBException> {

    @Override
    public Response toResponse(DWBException exception) {
        return Response.status(exception.getCode())
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
