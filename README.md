# Demo app for Hibernate Reactive Data Repositories on Quarkus

[Hibernate Data Repositories](https://hibernate.org/repositories) is an implementation of Jakarta Data backed by Hibernate ORM.
This project shows Hibernate Data Repositories used from Quarkus with Hibernate Reactive and RESTEasy Reactive.

Here we observe:

- Jakarta Persistence annotations used to declare and map entity types like `Book`, `Author`, and `Publisher`,
- Jakarta Data annotations used to declare a `Library` repository acting as a facade to Hibernate's `StatelessSession`, and
- JAX-RS annotations used to declare the frontend `LibraryResource`, with `Library` injected via CDI.- `RequestScopedStatelessSession` and `WithSessionInterceptor` provide make a reactive `StatelessSession` available to the reactive repository.

## Dependencies:

- Quarkus 3.21 or above, with RESTEasy Reactive and Jackson
- Jakarta Data 1.0.1
- Hibernate Reactive 2.4.7
- Hibernate Metamodel Generator 6.6.15

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/reactive-data-demo-quarkus-mvn-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and
  Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on
  it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus
  REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Reactive PostgreSQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the PostgreSQL
  database using the reactive pattern

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
