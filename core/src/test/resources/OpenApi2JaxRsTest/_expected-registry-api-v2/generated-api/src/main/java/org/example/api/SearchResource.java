package org.example.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.io.InputStream;
import java.util.List;
import org.example.api.beans.ArtifactSearchResults;
import org.example.api.beans.SortBy;
import org.example.api.beans.SortOrder;

/**
 * A JAX-RS interface.  An implementation of this interface must be provided.
 */
@Path("/v2/search")
public interface SearchResource {
  /**
   * Returns a paginated list of all artifacts that match the provided filter criteria.
   *
   */
  @Path("/artifacts")
  @GET
  @Produces("application/json")
  ArtifactSearchResults searchArtifacts(@QueryParam("name") String name,
      @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit,
      @QueryParam("order") SortOrder order, @QueryParam("orderby") SortBy orderby,
      @QueryParam("labels") List<String> labels, @QueryParam("properties") List<String> properties,
      @QueryParam("description") String description,
      @QueryParam("artifactgroup") String artifactgroup);

  /**
   * Returns a paginated list of all artifacts with at least one version that matches the
   * posted content.
   *
   */
  @Path("/artifacts")
  @POST
  @Produces("application/json")
  @Consumes("*/*")
  ArtifactSearchResults searchArtifactsByContent(@QueryParam("offset") Integer offset,
      @QueryParam("limit") Integer limit, @QueryParam("order") SortOrder order,
      @QueryParam("orderby") SortBy orderby, InputStream data);
}
