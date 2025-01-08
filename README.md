# java-spring-oauth2-client
An Oauth2 client application which consumes a [resource server API](https://github.com/eiriktve/ktor-oauth2-resource-server-api), 
using a token fetched from a [Spring Authorization Server](https://github.com/eiriktve/kotlin-spring-oauth2-authorization-server).

If you want a complete local Oauth2 setup with an Authorization server and a resource server, just click the links above
and follow the README to get them running. If not, then this repo can serve as a nice reference implementation which
you can use to set up your own client application.

## Technologies
- Java 21
- Gradle 8.x
- Spring boot 3.x
- Spring Oauth2 Client
- Token protected OpenAPI docs
- Metrics with Spring Actuator
- Integration tests with Wiremock and WebTestClient

## API-docs
OpenAPI / Swagger docs are available at **server:port/swagger**