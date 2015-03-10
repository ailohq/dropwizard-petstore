package com.example.resources;

import org.junit.FixMethodOrder;
import org.junit.Test;
import com.example.IntegrationTest;
import com.example.model.Dog;
import org.junit.runners.MethodSorters;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class DogIntegrationTest extends IntegrationTest {

    @Test
    public void createDog () {
        // given
        Dog dog = readFromJson("fixtures/dog.json", Dog.class);

        // when
        Response response = client.target(
                String.format("http://localhost:%d/dog", RULE.getLocalPort()))
                .request()
                .post(Entity.json(dog), Response.class);

        // then
        assertThat(response.getStatus()).isEqualTo(NO_CONTENT.getStatusCode());
    }

    @Test
    public void getAllDog () {
        // when
        List<Dog> dogList = client.target(
                String.format("http://localhost:%d/dog", RULE.getLocalPort()))
                .request()
                .get(List.class);

        // then
        assertThat(dogList).hasSize(1);
    }

    @Test
    public void getDogById () {
        // given
        Dog dog = readFromJson("fixtures/dog.json", Dog.class);

        // when
        Dog DogResult = client.target(
                String.format("http://localhost:%d/dog/1", RULE.getLocalPort()))
                .request()
                .get(Dog.class);

        // then
        assertThat(DogResult).isEqualToIgnoringGivenFields(dog, "id");
    }

}
