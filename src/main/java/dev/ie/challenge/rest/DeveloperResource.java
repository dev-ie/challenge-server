package dev.ie.challenge.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import dev.ie.challenge.model.ChallengeEntry;
import dev.ie.challenge.model.Developer;
import io.quarkus.infinispan.client.Remote;

@Path("/developers")
@Tag(name = "Developers", description = "Operations on Developers")
public class DeveloperResource {

    @Inject
    @Remote("developers")
    RemoteCache<String, Developer> developerCache;
    
    @Inject
    @Remote("entries")
    RemoteCache<String, ChallengeEntry> entryCache;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getDevelopers", description = "Lists all developers")
    @APIResponse(responseCode = "200", description = "A list of all developers", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Developer.class)))
    public List<Developer> list(){
        return developerCache.values().stream().collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "createDeveloper", description = "Creates a new developer with the provided display name")
    @APIResponse(responseCode = "201", description = "A new developer has been successfully created", content = @Content(schema = @Schema(implementation = Developer.class)))
    public Response create(@Context UriInfo uriInfo, String displayName){

        String id = UUID.randomUUID().toString();
        Developer developer = new Developer(id, displayName);
        developerCache.put(id, developer);

        return Response.created(uriInfo.getRequestUriBuilder().path(id).build())
                       .entity(developer)
                       .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getDeveloperById", description = "Reads the developers resource for a developer matching the provided id")
    @APIResponse(responseCode = "200", description = "The developer matching the provided ID was found", content = @Content(schema = @Schema(implementation = Developer.class)))
    @APIResponse(responseCode = "404", description = "No developer matching the provided ID was found")
    public Response getDeveloperById(@PathParam("id") String id){

        Developer developer = developerCache.get(id);
        if (developer == null){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(developer).build();
    }

    @GET
    @Path("{id}/entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getChallengeEntriesByDeveloperId", description = "Reads the list of challenge entries associated with a particular developer id")
    @APIResponse(responseCode = "200", description = "The developer matching the provided id was found", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ChallengeEntry.class)))
    @APIResponse(responseCode = "404", description = "No developer matching the provided id was found")
    public Response getChallengeEntriesByDeveloperId(@PathParam("id") String developerId){

        if (!developerCache.containsKey(developerId)){
            return Response.status(Status.NOT_FOUND).build();
        }

        QueryFactory queryFactory = Search.getQueryFactory(entryCache);
        Query<ChallengeEntry> query = queryFactory.create("from dev_ie.ChallengeEntry where developerId = :developerId");
        query.setParameter("developerId", developerId);

        List<ChallengeEntry> entries = query.execute().list();
        return Response.ok(entries).build();
    }
    
}
