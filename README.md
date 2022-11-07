# Koerber Backend 

This repository contains the development of an app as requested by the Koerber team's assignment.

## Use

Please download or clone the repository in order to have access to the files.

In order to use this application you need to have Docker installed in your machine.

Once you do, open a terminal and navigate to:

```bash
/src/main/docker
```

and run the command:

```bash
docker-compose -f docker-compose.yml up --detach --build
```

By doing this you will start the necessary containers to run the app.

You can head to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) and check if the application is up and running.

## Usage

The application provides, as stated before, a swagger that provides all the different endpoint that are exposed.

Two controllers exist that expose endpoints:

(The curl values are examples, they may not correspond to existing ids)

### Filter

#### [POST] /myfilter
```curl
curl --location --request POST 'http://localhost:8080/myfilter' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId" : "e4bdcb7d-6510-4b86-9ba6-2e05577834f2",
    "name": "testFilter2",
    "data": "testFilterData2",
    "outputFilter": "testOutput2",
    "screenId": "61dd27f5-1c92-4464-bb29-9395dd0841f7"
}'
```
#### [PUT] /myfilter
```curl
curl --location --request PUT 'http://localhost:8080/myfilter?deprecateBranches=true' \
--header 'Content-Type: application/json' \
--data-raw '{
    "correlationId": "9078c813-a8e7-4678-b5fe-324929a65559",
    "userId": "b83d82e9-71d7-455b-9fa0-9242986299b9",
    "name" : "updatedName"
}'
```
#### [DELETE] /myfilter
```curl
curl --location --request DELETE 'http://localhost:8080/myfilter' \
--header 'Content-Type: application/json' \
--data-raw '{
    "correlationId": "d32a311d-c353-415f-bee9-1389402d3ece",
    "userId": "b83d82e9-71d7-455b-9fa0-9242986299b9",
    "version" : 5
}'
```
#### [GET] /myfilter/{uuid}
```curl
curl --location --request GET 'http://localhost:8080/myfilter/d32a311d-c353-415f-bee9-1389402d3ece'
```
#### [GET] /myfilter/version/{uuid}
```curl
curl --location --request GET 'http://localhost:8080/myfilter/version/d32a311d-c353-415f-bee9-1389402d3ece?version=7'
```
#### [GET] /myfilter/latest
```curl
curl --location --request GET 'http://localhost:8080/myfilter/latest
```

### Branch

#### [POST] /branch
```curl
curl --location --request POST 'http://localhost:8080/branch' \
--header 'Content-Type: application/json' \
--data-raw '{
    "correlationId": "f421b268-4dc8-4977-b67f-1b01c47ccb25"
}'
```
#### [PUT] /branch
```curl
curl --location --request PUT 'http://localhost:8080/branch' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": "e4bdcb7d-6510-4b86-9ba6-2e05577834f2",
    "correlationId": "bc102feb-e120-453a-98a3-49c463ed6569",
    "name": "branchUpdatedBeforeMerge"
}'
```
#### [DELETE] /branch
```curl
curl --location --request DELETE 'http://localhost:8080/branch' \
--header 'Content-Type: application/json' \
--data-raw '{
    "correlationId": "192763f0-5bd8-4ea1-babe-6413b2cef9f4",
    "userId": "b83d82e9-71d7-455b-9fa0-9242986299b9"
}'
```
#### [GET] /branch
```curl
curl --location --request GET 'http://localhost:8080/branch?correlationId=a69a7568-5d8b-4012-be8d-a2cafd97a834&version=3'
```
#### [POST] /branch/merge
```curl
curl --location --request POST 'http://localhost:8080/branch/merge' \
--header 'Content-Type: application/json' \
--data-raw '{
    "correlationId": "bc102feb-e120-453a-98a3-49c463ed6569",
    "userId": "b83d82e9-71d7-455b-9fa0-9242986299b9"
}'
```

## Logs

This application provides a Kibana visualization of the aggregated logs. Access Kibana on the url [http://localhost:5601/](http://localhost:5601/).

Head to Discover tab and you will see the aggregate logs of the containers running. If you wish to focus on app logs, click on the magnifier symbol on "docker.container.image > docker-app"

## Notes

Created an abstract class to represent filters and branches seing as they have so many common properties. Created separated deleted tables to simplify the queries to get non deprecated filers and branches (essential its only an audit table, for reference).

Filters and branches have what I called a version correlation. This is a UUID that allows for the different versions of a given filter/branch to be correlated. Essentially it's a identifier for a filter/branch across versions.

The user field on filter/branch corresponds to the user that did the action (create/update). On the deleted tables, the user id corresponds to the user that did the last action and a new field exists to identify the user who deleted the entry.


## Shortcomings

- Due to time constraints (not enough time), I did not do unit tests and performance tests.
- No default data is being inserted through docker. Despite knowing the existance of "docker-entrypoint-initdb.d" to use with the postgres image, I had an issue derived from the way I'm generating the database. Seing as the app is the one generating the tables and the way I configured the containers forces the app to start after the postgres container, when the sql script runs on the container startup, the tables don't exist yet, so it's not able to insert the mock data. This obviously has a solution, I just didn't have the time to work arround the issue.



