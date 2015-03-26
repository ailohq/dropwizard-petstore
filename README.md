[![wercker status](https://app.wercker.com/status/5efb91bf7b7db2551da9d2b2eeff8d54/m "wercker status")](https://app.wercker.com/project/bykey/5efb91bf7b7db2551da9d2b2eeff8d54)
# Introduction
You have created an dropwizard application using lazybones. This application uses dropwizard 0.8.0 version.

If you are not already familiar with Dropwizard, check out [Getting Started](http://dropwizard.github.io/dropwizard/getting-started.html).

## Project Structure

    <proj>
        +- gradle
        |
        +- newrelic
        |
        +- src
            +- main
            |   +- java
            |   |     +- your.package.structure
            |   |           +- db
            |   |           +- health
            |   |           +- jersey
            |   |           +- model
            |   |           +- resources
            |   +- resources
            |
            +- test
                +- java
                |     +- // junit tests in here!
                +- resources
                      +- fixtures

## Running The Application
To test the example application run the following commands.

* To run the tests run

        gradle test

* To package the example run.

        gradle shadowJar


* To run the application run.

        gradle run

## Usage
Use either a browser or a REST client such as [PostMan](https://chrome.google.com/webstore/detail/postman-rest-client-packa/fhbjgbiflinjbdggehcddcbncdddomop?hl=en)

* You RESTful APIs are available here:

    http://localhost:8080/api/*
    http://localhost:8080/api/admin

* Static HTML pages are here:

    http://localhost:8080/

* [Swagger](http://swagger.io/) API are here:

    http://localhost:8080/api/api-docs

* Continuous Deployment wise, this project includes:

    Dockerfile to help it to build a docker image

    wercker.yml to help this run at wercker CI

    Procfile to help this application to be deployed in Heroku

## Code Style Guide
Use default IntelliJ setting for Java. If you use Eclipse or other IDE, please follow the example code style in this project.
