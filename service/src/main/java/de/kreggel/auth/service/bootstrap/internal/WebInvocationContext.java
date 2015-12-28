package de.kreggel.auth.service.bootstrap.internal;

import org.slf4j.MDC;

import javax.ws.rs.container.ResourceContext;

public class WebInvocationContext {

    private static final String REQUEST_ID = "RQID";

    private static final WebInvocationContext SINGLETON = new WebInvocationContext();

    private static ResourceContext resourceContext;

    private WebInvocationContext() {
    }

    public static WebInvocationContext getInstance() {
        return SINGLETON;
    }

    public void setResourceContext(ResourceContext newResourceContext) {
        resourceContext = newResourceContext;
    }

    public ResourceContext getResourceContext() {
        return resourceContext;
    }

    /**
     * Gets the current unique request id.
     *
     * @return the request id.
     */
    public String getRequestId() {
        return MDC.get(REQUEST_ID);
    }

    /**
     * Sets the current unique request id.
     *
     * @param id
     *            the id of the request to set or null to remove the id from the context.
     */
    public void setRequestId(String id) {

        if (id == null) {
            // make sure, that the current request id is always logged as well
            MDC.remove(REQUEST_ID);
        } else {
            MDC.put(REQUEST_ID, id);
        }
    }
}