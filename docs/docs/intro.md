# Getting Started

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
### Setting up MongoDB

There are several options for Installing MongoDB. 

#### Homebrew
```shell 
  brew install mongodb-community  
```
#### Community on Windows

To install MongoDB Community on Windows follow these instructions [MongoDB Community on Windows](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-windows/)

#### Docker
For details on how to run MongoDB in docker, see this link.  [MongoDB Community with Docker](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-community-with-docker/)


#### Atlas
You can also use a Free tier instances from MongoDB Atlas. To get started see [MongoDB Atlas Free Tier](https://www.mongodb.com/lp/cloud/atlas/try4-reg?utm_source=google&utm_campaign=search_gs_pl_evergreen_atlas_core-high-int_retarget-brand_gic-null_amers-us_ps-all_desktop_eng_lead&utm_term=mongodb%20atlas&utm_medium=cpc_paid_search&utm_ad=e&utm_ad_campaign_id=22194044121&adgroup=174717502979&cq_cmp=22194044121&gad_source=1&gclid=CjwKCAjwp8--BhBREiwAj7og15pdOvpj47L39jjkQe6-2qRBApmMpA4co4XbVUvmu5BaGUYEzhUWURoCegMQAvD_BwE)

:::tip

To complete the advanced topics you will need to use MongoDB Atlas. You can run Atlas via docker with the instructions found here
[Atlas in Docker](https://www.mongodb.com/docs/atlas/cli/current/atlas-cli-deploy-docker/)
:::


Your connection string is going to be similar to
` mongodb://localhost:27017 `. The specific port that shows will depend on the port mapptings you provided the Atlas docker container when you started it. 

## Build the application

Lets build the application to see that it runs successfully.

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

If you get to this point you have everything ready to start the lab. Click on the link below for "Saving Objects" to start. 


