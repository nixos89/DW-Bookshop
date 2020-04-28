# DW-Bookshop

How to start the DW-Bookshop application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/HelloWorld-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter one of the following urls:<br/>
  [`http://localhost:8080/api/books`](http://localhost:8080/books) <br/>
  [`http://localhost:8080/api/categories`](http://localhost:8080/categories) <br/>
  [`http://localhost:8080/api/authors`](http://localhost:8080/authors) <br/>
  
For other urls check Resource classes.  

## Description
  Dropwizard app using RESTful web service to create bookshop store for testing purposes for Master Thesis
