package com.example.health;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class DogHealthCheck extends InjectableHealthCheck {
    @Override
    public String getName() {
        return "Lazybones Health";
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
