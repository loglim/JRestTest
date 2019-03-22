package cz.loglim.jrest.controller;

import cz.loglim.jrest.dao.ItemDao;
import cz.loglim.jrest.res.Item;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/items")
public class ItemsController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAll() {
        return ItemDao.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Item item = ItemDao.get(parseId(id));
        return item.getId() != null ? Response.ok(item, MediaType.APPLICATION_JSON_TYPE).build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(Item item) {
        item.setId(null);
        return ItemDao.add(item) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") String id, Item item) {
        return item != null && ItemDao.update(parseId(id), item) ? Response.ok().build() : Response.status(
                Response.Status.BAD_REQUEST).build();
    }

    /**
     * Deletes item specified by id, if such exists
     *
     * @param id id of an existing item
     * @return Response.ok or Status.BAD_REQUEST
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        return ItemDao.delete(parseId(id)) ? Response.ok().build() : Response.status(
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
                    List<Item> all = ItemDao.getAll();
                    return all.size() > 0 ? all.get(0).getId() : -1;
                }
                case "last": {
                    List<Item> all = ItemDao.getAll();
                    return all.size() > 0 ? all.get(all.size() - 1).getId() : -1;
                }
                default:
                    return -1;
            }
        }
    }

}
