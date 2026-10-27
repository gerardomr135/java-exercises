# Introduction

This document contains installation instruction about how to run mongodb in docker on a local environment.

Expectation is that you use Windows OS for development.


# Install Rancher Desktop

Navigate to Rancher Desktop website: https://rancherdesktop.io/

You find "Download for Windows (x64)" on the page. Download and install it.


# Start Rancher Desktop

After successful installation, start.
- Kubernetes - not needed
- At the first run it takes longer time. (See progress indicator at the bottom).

Note: you can configure Rancher Desktop to run each time you start Windows.


# Start MongoDB

Start terminal (Windows cmd), and navigate to the following folder: `<project>/docker`

Execute the following command: `docker-compose up`

It will download docker image for MongoDB and Mongo Express (MongoDB Management tool).

Note: it will take more time at the first time due to downloading of images. Later it is faster.

You can check container state in Rancher Desktop, Containers view.
- docker-mongo-1
- docker-mongo-express-1


# Access Mongo Express

Open browser and navigate to: http://localhost:8081/

Login: admin/pass

Note: you do not need to use Mongo Express directly, but can be good for checking the database state.


# Create user

When your java application connects MongoDB, it needs to authenticate.
In this step you find instructions on how to create a new user in MongoDB.

Open Windows cmd and execute the following command
to connect the docker container where MongoDB is running.

    docker exec -it docker-mongo-1 bash

Change Operating System user:

    su mongodb

Open mongo shell:

    mongosh -u root -p password

After successful login, create a new user (execute the commands one by one):

    use tododb
    db.createUser({user: "todouser", pwd: "password", roles: [{role: "readWrite", db: "tododb"}]})

You can verify the created user in Mongo Express.
Navigate to http://localhost:8081/db/admin/system.users

You should see:

![todouser](https://raw.githubusercontent.com/epam-java-cre/exercise-specification-images/main/nosql-todo/MongoDB-user.png)
