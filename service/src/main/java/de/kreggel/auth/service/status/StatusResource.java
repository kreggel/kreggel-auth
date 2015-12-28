package de.kreggel.auth.service.status;

import de.kreggel.auth.service.bootstrap.internal.AbstractResource;

import javax.ws.rs.GET;

public class StatusResource extends AbstractResource {
    public final static Status STATUS = new Status();

    @GET
    public Status getStatus() {
        return STATUS;
    }
}
