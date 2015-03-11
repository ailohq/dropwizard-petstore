package com.example;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.nefariouszhen.dropwizard.assets.ConfiguredAssetsBundle;
import com.netflix.governator.guice.LifecycleInjector;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.model.ApiInfo;
import com.wordnik.swagger.reader.ClassReaders;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PetStore extends Application<PetStoreConfiguration> {

    public static void main(String[] args) throws Exception {
        new PetStore().run(args);
    }

    @Override
    public void initialize(final Bootstrap<PetStoreConfiguration> bootstrap) {
        bootstrap.addBundle(new ConfiguredAssetsBundle( "/assets/", "/", "index.html" ) );
        bootstrap.addBundle(new Java8Bundle());
        bootstrap.addBundle(new TemplateConfigBundle());
        bootstrap.addBundle(migrationsBundle);
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(guiceGovernatorBundle);
    }

    @Override
    public void run(PetStoreConfiguration configuration, Environment environment) throws Exception {
        setUpSwagger(environment);
    }

    private void setUpSwagger(final Environment environment) {
        // Swagger Resource
        environment.jersey().register(new ApiListingResourceJSON());

        // Swagger providers
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());

        // Swagger Scanner, which finds all the resources for @Api Annotations
        ScannerFactory.setScanner(new DefaultJaxrsScanner());

        // Add the reader, which scans the resources and extracts the resource information
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        // Set the swagger config options
        ApiInfo apiInfo= new ApiInfo(this.getName(), "description of service", null, "contact me", null, null );
        ConfigFactory.config().setApiInfo(apiInfo);
    }

    final MigrationsBundle<PetStoreConfiguration> migrationsBundle =
            new MigrationsBundle<PetStoreConfiguration>() {
                @Override
                public DataSourceFactory getDataSourceFactory(PetStoreConfiguration configuration) {
                    return configuration.getDataSource();
                }
            };

    final HibernateBundle<PetStoreConfiguration> hibernateBundle =
            new ScanningHibernateBundle<PetStoreConfiguration>("com.example.model") {
                @Override
                public DataSourceFactory getDataSourceFactory(PetStoreConfiguration configuration) {
                    return configuration.getDataSource();
                }
            };

    final GuiceBundle<PetStoreConfiguration> guiceGovernatorBundle = GuiceBundle.<PetStoreConfiguration>newBuilder()
            .addModule(new PetStoreModule(hibernateBundle))
            .enableAutoConfig(
                    "com.example.resources",
                    "com.example.health",
                    "com.example.jersey"
            )
            .setConfigClass(PetStoreConfiguration.class)
            .setInjectorFactory((stage, modules) -> LifecycleInjector.builder()
                    .inStage(stage)
                    .withModules(modules)
                    .build()
                    .createInjector())
            .build();

}
