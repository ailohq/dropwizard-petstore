package com.example.resources;

import java.util.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import com.example.JsonFixtureTest;
import com.example.db.DogDAO;
import com.example.model.Dog;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import io.dropwizard.java8.jersey.OptionalMessageBodyWriter;
import io.dropwizard.java8.jersey.OptionalParamFeature;

@RunWith(MockitoJUnitRunner.class)
public class DogResourceTest extends JsonFixtureTest {

    private final static DogDAO dogDAO = mock(DogDAO.class);
    private final Dog dog = readFromJson("fixtures/dog.json", Dog.class);

    @ClassRule
    public static ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new DogResource(dogDAO))
            .addProvider(OptionalMessageBodyWriter.class)
            .addProvider(OptionalParamFeature.class)
            .build();

    @Before
    public void setUp () {
        // given
        when(dogDAO.readById(1)).thenReturn(Optional.of(dog));
        when(dogDAO.readAll()).thenReturn(Arrays.<Dog>asList(dog));
    }

    @Test
    public void createDog () {

        // when
        resources.client().target("/dog").request().post(Entity.json(dog));

        // then
        verify(dogDAO).create(any(Dog.class));
    }


    @Test
    public void getDogById () {

        // when
        Response response = resources.client().target("/dog/1").request().get();
        Dog result = resources.client().target("/dog/1").request().get(Dog.class);

        // then
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(result).isEqualToComparingFieldByField(dog);
    }


    @Test
    public void getAllDog () {

        // when
        Response response = resources.client().target("/dog").request().get();
        List<Dog> result = resources.client().target("/dog").request().get(List.class);

        // then
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(result.size()).isEqualTo(1);
    }

}
