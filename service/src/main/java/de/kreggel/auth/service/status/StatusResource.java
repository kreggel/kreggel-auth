package de.kreggel.auth.service.status;

import de.kreggel.auth.core.configuration.AuthConfig;
import de.kreggel.auth.service.bootstrap.internal.AbstractResource;

import javax.inject.Inject;
import javax.ws.rs.GET;

public class StatusResource extends AbstractResource {
    public final static Status STATUS = new Status();

    @Inject
    private AuthConfig config;

    @GET
    public Status getStatus() {
        System.out.println("StatusResourec: "+config);
        return STATUS;
    }
}
