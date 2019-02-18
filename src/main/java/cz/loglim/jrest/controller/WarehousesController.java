package cz.loglim.jrest.controller;

import cz.loglim.jrest.dao.WarehouseDao;
import cz.loglim.jrest.res.Warehouse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/warehouses")
public class WarehousesController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Warehouse> getAll() {
        WarehouseDao.begin();
        List<Warehouse> warehouses = WarehouseDao.getAll();
        WarehouseDao.end();
        return warehouses;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Warehouse get(@PathParam("id") int id){
        WarehouseDao.begin();
        Warehouse warehouse = WarehouseDao.get(id);
        WarehouseDao.end();
        return warehouse;
    }

    @POST
    @Consumes("application/json")
    public Response add(Warehouse Warehouse){
        WarehouseDao.begin();
        Warehouse.setId(null);
        Response response = WarehouseDao.add(Warehouse) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        WarehouseDao.end();
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") int id, Warehouse Warehouse){
        WarehouseDao.begin();
        Response response = Warehouse != null && WarehouseDao.update(id, Warehouse) ? Response.ok().build(): Response.status(Response.Status.BAD_REQUEST).build();
        WarehouseDao.end();
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        WarehouseDao.begin();
        Response response = WarehouseDao.delete(id) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        WarehouseDao.end();
        return response;
    }

}
