package com.example.db;

import java.util.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import com.example.model.Dog;

import javax.inject.Inject;
import java.util.List;

public class DogDAO extends AbstractDAO<Dog> {

    @Inject
    public DogDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void create(Dog dog) {
        currentSession().persist(dog);
    }

    public List<Dog> readAll() {
        return list(currentSession().createCriteria(Dog.class));
    }

    public Optional<Dog> readById(Integer id) {
        return Optional.ofNullable(get(id));
    }
}
