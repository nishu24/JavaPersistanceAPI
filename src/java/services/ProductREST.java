/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Product;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author c0646929
 */
@Path("/product")
@RequestScoped
public class ProductREST {

    @PersistenceContext(unitName = "WebApplication40PU")
    EntityManager em;

    @Inject
    UserTransaction transaction;

    List<Product> productList;
   // rs/product
    @GET
    @Produces("application/json")
    public Response getAll() {
        Query q = em.createNamedQuery("Product.findAll");
        productList = q.getResultList();
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Product p : productList) {
            json.add(p.toJSON());
        }
        return Response.ok(json.build().toString()).build();
    }
        
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        Query q = em.createQuery("SELECT p FROM Product p WHERE p.productId = :productId");
        q.setParameter("productId", id);
        Product p = (Product) q.getSingleResult();
        return Response.ok(p.toJSON().toString()).build();
    }
    
    
    @POST
    @Consumes("application/json")  // write in content-type 
    public Response add(JsonObject json) {
        Response result;
        try {
            transaction.begin();
            Product p = new Product(json);
            em.persist(p);
            transaction.commit();
            result = Response.ok().build();
        } catch (Exception ex) {
            result = Response.status(500).entity(ex.getMessage()).build();
        }
        return result;
    }
        
    @PUT
    @Consumes("application/json")  // update/change mehod for data,,,,response should be around 200 okays
    public Response edit(JsonObject json) {
        Response result;
        try {
            transaction.begin();
            Product p = (Product) em.createNamedQuery("Product.findByProductId")
                    .setParameter("productId", json.getInt("productId")).getSingleResult();
            p.setName(json.getString("name"));
            p.setDescription(json.getString("description"));
            p.setQuantity(json.getInt("quantity"));
            em.persist(p);
            transaction.commit();
            result = Response.ok().build();
        } catch (Exception ex) {
            result = Response.status(500).entity(ex.getMessage()).build();
        }
        return result;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        Response result;
        try {
            transaction.begin();
            Product p = (Product) em.createNamedQuery("Product.findByProductId")
                    .setParameter("productId", id).getSingleResult();
            em.remove(p);
            transaction.commit();
            result = Response.ok().build();
        } catch (Exception ex) {
            result = Response.status(500).entity(ex.getMessage()).build();
        }
        return result;
    }
}
