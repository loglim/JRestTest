package cz.loglim.jrest.controller;

import cz.loglim.jrest.dao.ItemDaoSimple;
import cz.loglim.jrest.res.Item;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/v0/items")
public class ItemsControllerSimple {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAll() {
        return ItemDaoSimple.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item get(@PathParam("id") int id){
        Item item = null;
        try {
            item = ItemDaoSimple.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item == null ? new Item() : item;
    }

    @POST
    @Consumes("application/json")
    public Response add(Item item){
        item.setId(null);
        return ItemDaoSimple.add(item) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") int id, Item item){
        return item != null && ItemDaoSimple.update(id, item) ? Response.ok().build(): Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        return ItemDaoSimple.delete(id) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

}
