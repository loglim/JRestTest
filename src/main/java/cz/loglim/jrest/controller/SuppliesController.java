package cz.loglim.jrest.controller;

import cz.loglim.jrest.dao.SupplyDao;
import cz.loglim.jrest.res.Supply;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/supplies")
public class SuppliesController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Supply> getAll() {
        return SupplyDao.getAll();
    }

    @POST
    @Consumes("application/json")
    public Response add(Supply supply) {
        return SupplyDao.add(supply) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") long id, Supply supply) {
        return supply != null && SupplyDao.update(id, supply) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        return SupplyDao.delete(id) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

}
