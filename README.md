# MongoBank

## Purpose
MongoBank is a hands-on tutorial for developers that are wanting to get more comfortable with developing against the MongoDB database. Based on spring-boot and spring-data
the work shops leads users through the process of creating a simulated banking app with customers, accounts, and transactions. 

## Getting Started

Assumptions:
* Java is installed on your system
* Docker is installed on your machine
* Maven or Gradle is installed on your machine 


1. Setup Database

The system, whenm completed, is dependent on some features only available in MongoDB Atlas (Cloud Database as a Service). Fortunately we can use the Atlas Docker image to 
test our code for functionality in a local environment. 

Full instructions on how to download and setup Atlas via docker are here [Atlas on Docker](https://www.mongodb.com/docs/atlas/cli/current/atlas-cli-deploy-docker/)


1. Download Code Repository

Clone this github repo to a location on your machine. 

```shell
    git clone xxxxxx
```


2. Update connection strings

Create a file named `.env` in the root of your project. Inside that file add the following entries

```
MONGODB_URI= <your connection string here>

```

Your connection string is going to be similar to 
` mongodb://localhost:27017 `. The specific port that shows will depend on the port mapptings you provided the Atlas docker container when you started it. 

3. Build the application

Lets build the application to see that it run successfully. 

```shell
mvn spring-boot:run 
```
or 
``` shell 
./gradlew bootRun
```
Depending on the build tool you prefer to use. 

3. Test that API calls work

The base system provides REST API endpoints, so lets verify that those are working and returning content. 

```shell
curl locahost:8080/api/hello -H "Content-Type:application/json"
```

The response should look like 

```
Hello World
```

### Start the Hands-On Lab

At this point the system is up and working, and you are ready to start your hands-on lab. Enjoy the process and we hope you learn about the benefits and ease of working with MongoDB and Java. 

* [Lab 1: Persistence and Query](./LAB1.md)
* [Lab 2: Relationships and Transactions ](./LAB2.md)
* [lab 3: Advanced Topics](./LAB3.md)


