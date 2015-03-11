package com.example.resources;

import com.example.IntegrationTest;
import com.example.model.Dog;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class DogIntegrationTest extends IntegrationTest {

    private final String testUrl = String.format("http://localhost:%d/api/dog", RULE.getLocalPort());

    @Test
    public void createDog () {
        // given
        Dog dog = readFromJson("fixtures/dog.json", Dog.class);

        // when
        Response response = client.target(testUrl)
                .request()
                .post(Entity.json(dog), Response.class);

        // then
        assertThat(response.getStatus()).isEqualTo(NO_CONTENT.getStatusCode());
    }

    @Test
    public void getAllDog () {
        // when
        List<Dog> dogList = client.target(testUrl)
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
        Dog DogResult = client.target(testUrl + "/1")
                .request()
                .get(Dog.class);

        // then
        assertThat(DogResult).isEqualToIgnoringGivenFields(dog, "id");
    }

}
