package de.kreggel.auth.service.status;

import de.kreggel.auth.service.bootstrap.internal.AbstractResource;

import javax.inject.Inject;
import javax.ws.rs.GET;

public class VersionResource extends AbstractResource {
    @Inject
    private Version version;

    @GET
    public Version getVersion() {
        System.out.println(version);
        return version;
    }
}
