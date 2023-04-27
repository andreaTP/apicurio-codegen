package org.example.api;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.example.api.beans.Widget;

/**
 * A JAX-RS interface. An implementation of this interface must be provided.
 */
@Path("/widgets")
public interface WidgetsResource {
  /**
   * <p>
   * Gets a list of all <code>Widget</code> entities.
   * </p>
   * 
   */
  @GET
  @Produces("application/json")
  List<Widget> getWidgets();

  /**
   * <p>
   * Creates a new instance of a <code>Widget</code>.
   * </p>
   * 
   */
  @POST
  @Consumes("application/json")
  void createWidget(@NotNull Widget data);

  /**
   * <p>
   * Gets the details of a single instance of a <code>Widget</code>.
   * </p>
   * 
   */
  @Path("/{widgetId}")
  @GET
  @Produces("application/json")
  Response getWidget(@PathParam("widgetId") String widgetId);

  /**
   * <p>
   * Updates an existing <code>Widget</code>.
   * </p>
   * 
   */
  @Path("/{widgetId}")
  @PUT
  @Consumes("application/json")
  void updateWidget(@PathParam("widgetId") String widgetId, @NotNull Widget data);

  /**
   * <p>
   * Deletes an existing <code>Widget</code>.
   * </p>
   * 
   */
  @Path("/{widgetId}")
  @DELETE
  void deleteWidget(@PathParam("widgetId") String widgetId);
}
