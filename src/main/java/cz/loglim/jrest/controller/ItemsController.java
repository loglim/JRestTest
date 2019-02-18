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
        ItemDao.begin();
        List<Item> items = ItemDao.getAll();
        ItemDao.end();
        return items;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item get(@PathParam("id") int id){
        ItemDao.begin();
        Item item = ItemDao.get(id);
        ItemDao.end();
        return item;
    }

    @POST
    @Consumes("application/json")
    public Response add(Item item){
        ItemDao.begin();
        item.setId(null);
        Response response = ItemDao.add(item) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        ItemDao.end();
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") int id, Item item){
        ItemDao.begin();
        Response response = item != null && ItemDao.update(id, item) ? Response.ok().build(): Response.status(Response.Status.BAD_REQUEST).build();
        ItemDao.end();
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        ItemDao.begin();
        Response response = ItemDao.delete(id) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
        ItemDao.end();
        return response;
    }

}
