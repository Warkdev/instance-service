package eu.getmangos.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import eu.getmangos.dto.CharacterInstanceDTO;
import eu.getmangos.dto.CreatureRespawnDTO;
import eu.getmangos.dto.GameobjectRespawnDTO;
import eu.getmangos.dto.GroupInstanceDTO;
import eu.getmangos.dto.InstanceDTO;

public interface InstanceResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a list of instances from the database.",
        description = "This API is returning a list of instances from the database. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of instances", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = InstanceDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findAllInstances(@QueryParam("page") Integer page, @QueryParam("page_size") Integer pageSize);

    @GET
    @Path("/{instance_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves an instance from the database.",
        description = "This API is returning an instance from the database matching the given ID. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The matching instance", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = InstanceDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findInstance(@PathParam("instance_id") Integer instanceId);

    @GET
    @Path("/map/{map_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a list of instances for a given Map ID from the database.",
        description = "This API is returning a list of instances from the database matching the given Map ID. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of instances matching the map", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = InstanceDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findAllInstancesByMap(@PathParam("map_id") Integer map, @QueryParam("page") Integer page, @QueryParam("page_size") Integer pageSize);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new instance within the database.",
        description = "This API is creating a new instance within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "201", description = "The instance has been created"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response createInstance(InstanceDTO entity);

    @PUT
    @Path("/{instance_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an instance within the database.",
        description = "This API is updating an instance within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The instance has been updated"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response updateInstance(@PathParam("instance_id") Integer instanceId, InstanceDTO entity);

    @DELETE
    @Path("/{instance_id}")
    @Operation(summary = "Delete an instance from the database",
        description = "This API is deleting an existing instance based on the provided id."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The instance has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteInstance(@PathParam("instance_id") Integer instanceId);

    @PUT
    @Path("/pack")
    @Operation(summary = "Pack instances within the database",
        description = "This API is packing all instances, reducing their ID and starting at 1."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The instances have been packed", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response packInstances();

    @GET
    @Path("/{instance_id}/creature_respawn")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a list of creature respawn timers for the given instance from the database.",
        description = "This API is returning a list of creature respawn timers from the database. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of creature respawn timers", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = CreatureRespawnDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findAllCreatureRespawn(@PathParam("instance_id") Integer instanceId, @QueryParam("page") Integer page, @QueryParam("page_size") Integer pageSize);

    @GET
    @Path("/{instance_id}/creature_respawn/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a specific creature respawn timer for the given instance from the database.",
        description = "This API is returning a specific creature respawn timer from the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of creature respawn timers", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = CreatureRespawnDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "404", description = "Timer not found"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findCreatureRespawn(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @POST
    @Path("/{instance_id}/creature_respawn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new creature respawn timer within the database for the given instance.",
        description = "This API is creating a new creature respawn timer within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "201", description = "The respawn timer has been created"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response createCreatureRespawn(@PathParam("instance_id") Integer instanceId, CreatureRespawnDTO entity);

    @PUT
    @Path("/{instance_id}/creature_respawn/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a creature respawn timer within the database.",
        description = "This API is updating a creature respawn timer within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The creature respawn timer has been updated"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response updateCreatureRespawnTimer(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid, CreatureRespawnDTO entity);

    @DELETE
    @Path("/{instance_id}/creature_respawn")
    @Operation(summary = "Delete all creature respawn timer for the given instance",
        description = "This API is deleting all existing creature respawn timers for the given instance ID."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The creature respawn timers has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteAllCreatureRespawnTimer(@PathParam("instance_id") Integer instanceId);

    @DELETE
    @Path("/{instance_id}/creature_respawn/{guid}")
    @Operation(summary = "Delete a creature respawn timer",
        description = "This API is deleting an existing creature respawn timer based on the provided id."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The creature respawn timer has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteCreatureRespawnTimer(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @GET
    @Path("/{instance_id}/gameobject_respawn")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a list of gameobject respawn timers for the given instance from the database.",
        description = "This API is returning a list of gameobject respawn timers from the database. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of gameobject respawn timers", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = GameobjectRespawnDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findAllGameobjectRespawn(@PathParam("instance_id") Integer instanceId, @QueryParam("page") Integer page, @QueryParam("page_size") Integer pageSize);

    @GET
    @Path("/{instance_id}/gameobject_respawn/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a specific gameobject respawn timer for the given instance from the database.",
        description = "This API is returning a specific gameobject respawn timer from the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of gameobject respawn timers", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = GameobjectRespawnDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "404", description = "Timer not found"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findGameobjectRespawn(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @POST
    @Path("/{instance_id}/gameobject_respawn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new gameobject respawn timer within the database for the given instance.",
        description = "This API is creating a new gameobject respawn timer within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "201", description = "The respawn timer has been created"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response createGameobjectRespawn(@PathParam("instance_id") Integer instanceId, GameobjectRespawnDTO entity);

    @PUT
    @Path("/{instance_id}/gameobject_respawn/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a gameobject respawn timer within the database.",
        description = "This API is updating a gameobject respawn timer within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The gameobject respawn timer has been updated"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response updateGameobjectRespawnTimer(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid, GameobjectRespawnDTO entity);

    @DELETE
    @Path("/{instance_id}/gameobject_respawn")
    @Operation(summary = "Delete all gameobject respawn timer for the given instance",
        description = "This API is deleting all existing gameobject respawn timers for the given instance ID."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The gameobject respawn timers has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteAllGameobjectRespawnTimer(@PathParam("instance_id") Integer instanceId);

    @DELETE
    @Path("/{instance_id}/gameobject_respawn/{guid}")
    @Operation(summary = "Delete a gameobject respawn timer",
        description = "This API is deleting an existing gameobject respawn timer based on the provided id."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The gameobject respawn timer has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteGameobjectRespawnTimer(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @GET
    @Path("/{instance_id}/character")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a list of character links for the given instance from the database.",
        description = "This API is returning a list of character links from the database. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of character links", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = GameobjectRespawnDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findAllCharacterInstance(@PathParam("instance_id") Integer instanceId, @QueryParam("page") Integer page, @QueryParam("page_size") Integer pageSize);

    @GET
    @Path("/{instance_id}/character/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a specific character link for the given instance from the database.",
        description = "This API is returning a specific character link from the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of character links timers", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = CharacterInstanceDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "404", description = "Link not found"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findCharacterInstance(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @POST
    @Path("/{instance_id}/character")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new character link within the database for the given instance.",
        description = "This API is creating a new character link within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "201", description = "The link has been created"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response createCharacterInstance(@PathParam("instance_id") Integer instanceId, CharacterInstanceDTO entity);

    @PUT
    @Path("/{instance_id}/character/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a character link within the database.",
        description = "This API is updating a character link within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The character link has been updated"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response updateCharacterInstance(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid, CharacterInstanceDTO entity);

    @DELETE
    @Path("/{instance_id}/character")
    @Operation(summary = "Delete all character link for the given instance",
        description = "This API is deleting all existing character links for the given instance ID."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The character links has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteAllCharacterInstance(@PathParam("instance_id") Integer instanceId);

    @DELETE
    @Path("/{instance_id}/character/{guid}")
    @Operation(summary = "Delete a character link",
        description = "This API is deleting an existing character link based on the provided id."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The character link has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteCharacterInstance(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @GET
    @Path("/{instance_id}/group")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a list of group links for the given instance from the database.",
        description = "This API is returning a list of group links from the database. A missing page and page size will return the first 20 results."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of group links", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = GameobjectRespawnDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findAllGroupInstance(@PathParam("instance_id") Integer instanceId, @QueryParam("page") Integer page, @QueryParam("page_size") Integer pageSize);

    @GET
    @Path("/{instance_id}/group/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves a specific group link for the given instance from the database.",
        description = "This API is returning a specific group link from the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "A list of group links timers", content = @Content(
                        mediaType = "application/json", schema = @Schema(implementation = GroupInstanceDTO.class)
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "404", description = "Link not found"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response findGroupInstance(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);

    @POST
    @Path("/{instance_id}/group")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new group link within the database for the given instance.",
        description = "This API is creating a new group link within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "201", description = "The link has been created"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response createGroupInstance(@PathParam("instance_id") Integer instanceId, GroupInstanceDTO entity);

    @PUT
    @Path("/{instance_id}/group/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a group link within the database.",
        description = "This API is updating a group link within the database."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "The group link has been updated"),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected event occured")
        }
    )
    public Response updateGroupInstance(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid, GroupInstanceDTO entity);

    @DELETE
    @Path("/{instance_id}/group")
    @Operation(summary = "Delete all group link for the given instance",
        description = "This API is deleting all existing group links for the given instance ID."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The group links has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteAllGroupInstance(@PathParam("instance_id") Integer instanceId);

    @DELETE
    @Path("/{instance_id}/group/{guid}")
    @Operation(summary = "Delete a group link",
        description = "This API is deleting an existing group link based on the provided id."
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "The group link has been deleted", content = @Content(
                        mediaType = "application/json"
                )
            ),
            @APIResponse(responseCode = "400", description = "Error with the request"),
            @APIResponse(responseCode = "500", description = "An unexpected even occured")
        }
    )
    public Response deleteGroupInstance(@PathParam("instance_id") Integer instanceId, @PathParam("guid") Integer guid);
}