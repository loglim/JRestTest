package cz.loglim.jrest.controller;

import cz.loglim.jrest.dao.WarehouseDao;
import cz.loglim.jrest.res.Warehouse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/v1/warehouses")
public class WarehousesController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Warehouse> getAll() {
        return WarehouseDao.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Warehouse warehouse = WarehouseDao.get(parseId(id));
        return warehouse.getId() != null ? Response.ok(warehouse,
                MediaType.APPLICATION_JSON_TYPE).build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(Warehouse warehouse) {
        warehouse.setId(null);
        if (warehouse.getLastAccess() == null) {
            warehouse.setLastAccess(LocalDateTime.now());
        }
        return WarehouseDao.add(warehouse) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") String id, Warehouse warehouse) {
        return warehouse != null && WarehouseDao.update(parseId(id),
                warehouse) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        return WarehouseDao.delete(parseId(id)) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    /**
     * Tries to parse id or use find one using order keyword (eg. first, last)
     * Must be called withing active ItemDao context
     *
     * @param textId text representation of id or order keyword
     * @return parsed or found id; -1 on invalid request
     */
    private static long parseId(String textId) {
        try {
            return Long.parseLong(textId);
        } catch (NumberFormatException exception) {
            switch (textId.toLowerCase()) {
                case "first": {
                    List<Warehouse> all = WarehouseDao.getAll();
                    return all.size() > 0 ? all.get(0).getId() : -1;
                }
                case "last": {
                    List<Warehouse> all = WarehouseDao.getAll();
                    return all.size() > 0 ? all.get(all.size() - 1).getId() : -1;
                }
                default:
                    return -1;
            }
        }
    }

}
