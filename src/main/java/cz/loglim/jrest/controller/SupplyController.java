package cz.loglim.jrest.controller;

import cz.loglim.jrest.dao.SupplyDao;
import cz.loglim.jrest.dao.SupplyDao;
import cz.loglim.jrest.res.Item;
import cz.loglim.jrest.res.Supply;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/supplies")
public class SupplyController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Supply> getAll() {
        SupplyDao.begin();
        List<Supply> supplies = SupplyDao.getAll();
        SupplyDao.end();
        return supplies;
    }

    @POST
    @Consumes("application/json")
    public Response add(Supply item){
        SupplyDao.begin();
        Response response = SupplyDao.add(item) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        SupplyDao.end();
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") int id, Supply supply){
        SupplyDao.begin();
        Response response = supply != null && SupplyDao.update(id, supply) ? Response.ok().build(): Response.status(Response.Status.BAD_REQUEST).build();
        SupplyDao.end();
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        SupplyDao.begin();
        Response response = SupplyDao.delete(id) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        SupplyDao.end();
        return response;
    }

}
