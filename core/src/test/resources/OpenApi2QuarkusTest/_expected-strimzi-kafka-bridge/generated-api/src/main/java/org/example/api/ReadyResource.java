package org.example.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * A JAX-RS interface.  An implementation of this interface must be provided.
 */
@Path("/ready")
public interface ReadyResource {
  /**
   * Check if the bridge is ready and can accept requests.
   */
  @GET
  void ready();
}
