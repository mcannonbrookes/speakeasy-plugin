package com.atlassian.labs.speakeasy.rest;

import com.atlassian.labs.speakeasy.SpeakeasyManager;
import com.atlassian.labs.speakeasy.data.SpeakeasyData;
import com.atlassian.sal.api.user.UserManager;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/user")
public class UserResource
{
    private final SpeakeasyManager speakeasyManager;
    private final SpeakeasyData data;
    private final UserManager userManager;

    public UserResource(SpeakeasyManager speakeasyManager, UserManager userManager, SpeakeasyData data)
    {
        this.speakeasyManager = speakeasyManager;
        this.userManager = userManager;
        this.data = data;
    }

    @GET
    @Path("")
    public Response getPlugins()
    {
        return Response.ok(speakeasyManager.getUserAccessList(userManager.getRemoteUsername())).build();
    }

    @PUT
    @Path("devmode")
    public Response enableDevMode()
    {
        data.setDeveloperModeEnabled(userManager.getRemoteUsername(), true);
        return Response.ok().build();
    }

    @DELETE
    @Path("devmode")
    public Response disableDevMode()
    {
        data.setDeveloperModeEnabled(userManager.getRemoteUsername(), false);
        return Response.ok().build();
    }


    @PUT
    @Path("{pluginKey}")
    public Response enableAccess(@PathParam("pluginKey") String pluginKey)
    {
        speakeasyManager.allowUserAccess(pluginKey, userManager.getRemoteUsername());
        return Response.ok().build();
    }

    @DELETE
    @Path("{pluginKey}")
    public Response disableAccess(@PathParam("pluginKey") String pluginKey)
    {
        speakeasyManager.disallowUserAccess(pluginKey, userManager.getRemoteUsername());
        return Response.ok().build();
    }
}
