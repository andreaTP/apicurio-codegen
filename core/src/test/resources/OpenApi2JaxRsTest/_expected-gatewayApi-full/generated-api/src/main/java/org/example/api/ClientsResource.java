package org.example.api;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.example.api.beans.Client;

/**
 * A JAX-RS interface.  An implementation of this interface must be provided.
 */
@Path("/clients")
public interface ClientsResource {
  /**
   * Register a Client and make it immediately available on the gateway.
   */
  @PUT
  void registerAClient(Client body);
}
