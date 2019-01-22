package cz.loglim.jrest.res;

import cz.loglim.jrest.DAO;
import cz.loglim.jrest.model.Item;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("items")
public class Api {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        return DAO.getItems();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON) //alternatively @Produces("application/json") - TODO: Measure performance impact
    public Item getItem(@PathParam("id") int id){
        Item item = DAO.getItem(id);

        if(item == null) {
            return new Item();
        }
        else {
            return item;
        }
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    public Response addItem(Item item){
        item.setName(item.getName());
        item.setPrice(item.getPrice());

        DAO.addItem(item);

        return DAO.addItem(item) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes("application/json")
    public Response updateEmployee(@PathParam("id") int id, Item item){
        return DAO.updateItem(id, item) ? Response.ok().build(): Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes("application/json")
    public Response deleteEmployee(@PathParam("id") int id){
        return DAO.deleteItem(id) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

}
