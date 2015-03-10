package com.example.resources;

import javax.ws.rs.Consumes;
import com.wordnik.swagger.annotations.*;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;
import com.example.db.DogDAO;
import com.example.model.Dog;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/dog")
@Api(value = "/dog", description = "Operation on Dog")
@Produces(MediaType.APPLICATION_JSON)
public class DogResource {

    private final DogDAO dogDAO;

    @Inject
    public DogResource(final DogDAO dogDAO) {
        this.dogDAO = dogDAO;
    }

    @POST
    @Timed
    @UnitOfWork
    @Consumes(APPLICATION_JSON)
    @ApiOperation(value = "Create Dog")
    @ApiResponses(value = {@ApiResponse(code = 422, message = "Dog already exists")})
    public void createDog(@Valid Dog dog) {
        dogDAO.create(dog);
    }

    @GET
    @Timed
    @UnitOfWork
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    @ApiOperation(value = "Get all Dog", response = List.class)
    public List<Dog> getAllDog() {
        return dogDAO.readAll();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    @ApiOperation(value = "Get Dog by Id", response = Dog.class)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Dog not found")})
    public Optional<Dog> getDogById(@ApiParam(required = true) @PathParam("id") final Integer id ) {
        return dogDAO.readById(id);
    }

}
