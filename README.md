# DGS Federated GraphQL WIP

## Synopsis

This is a sample project that demonstrates how to use [Netflix DGS](https://netflix.github.io/dgs/) to build a federated
GraphQL API.  
It's a wip based on the content presented in [Code GraphQL Application : Java Spring Boot 3 & Netflix DGS  
](https://www.udemy.com/course/code-graphql-application-with-java-spring-boot-netflix-dgs/)   
The interesting thing about the course is the code isn't optimal, but that provides opportunities to refactor and
explore Java 17 language features.   
But the specific focus on DGS is what makes the course valuable.

## Motivation

* A federated GraphQL approach to microservice architecture promotes loose coupling between microservices, which in turn
  improves both deployability and individual testability.
* Individual testability is also improved because each microservice can be tested independently of the rest of the
  system. This means that developers can test their code in isolation, without worrying about the impact on other
  microservices. This reduces the risk of bugs and improves overall code quality.
* In a federated GraphQL approach, each microservice exposes a GraphQL schema that describes its data and functionality.
  These schemas are then combined into a single federated schema that spans the entire system. This allows clients to
  query the entire system using a single GraphQL endpoint.
* Because each microservice is responsible for its own schema, developers can work independently on different
  microservices without worrying about the impact on the rest of the system. This promotes loose coupling between
  microservices, which in turn improves both deployability and individual testability.
* Overall, a federated GraphQL approach to microservice architecture promotes a modular, scalable, and resilient system
  that is easy to deploy and test.
 
 ## Notes
 
 DGS's schema first approach may lend itself well to db decomposition via tools like [db2graphql](https://github.com/taviroquai/db2graphql)
  
  
