# DW-Bookshop

## Description
  Dropwizard app using RESTful web service to create bookshop store for testing purposes for Master Thesis

How to start the DW-Bookshop application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/DW-Bookshop-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter one of the following urls:<br/>
  [`http://localhost:8080/api/books`](http://localhost:8080/api/books) <br/>
  [`http://localhost:8080/api/categories`](http://localhost:8080/api/categories) <br/>
  [`http://localhost:8080/api/authors`](http://localhost:8080/api/authors) <br/>
  
For other urls check Resource classes in this [package](https://github.com/nixos89/DW-Bookshop/tree/master/src/main/java/com/nikolas/master_thesis/resources).  