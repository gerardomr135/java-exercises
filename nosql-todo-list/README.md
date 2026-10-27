
# NoSQL Todo List

In this exercise you are about to write a simple application in Spring and in which the data is stored in a NoSQL database.

Focus points are the following:
* Store data in NoSQL database (MongoDB)
* Apply appropriate layer to reach out for data
* Annotate object-data mapping accordingly

---

## Prerequisite knowledge

* Spring framework, Spring Boot
* Concept of interactive console application
* Concept of NoSQL databases

---


## Functionality

The application can be used to manage Todo items.
Using the console based user interface, the user can create Todo items, change state and delete an item.

After the application is started, it prints the following usage information:

```plaintext
Welcome in the Todo App!
Available commands:
  list
  create <priority> <date> <title>
  doing <idx>
  done <idx>
  remove <idx>
  exit
where <idx> is the index of the item in the list.
where <priority> is one of: NO, LOW, MEDIUM, HIGH.
where <date> is in the format: yyyy.MM.dd
```

Initially the database is empty, so the first meaningful command is the creation.

After creation, user can list the items, change the state of an item, or remove an item.


## Domain model

The objects that the application manages is quite simple. See below:

![](https://raw.githubusercontent.com/epam-java-cre/exercise-specification-images/main/nosql-todo/model.png)


## Technical Details

The template repository contains a working Spring Boot application that you can start.
The menu and all user interactions are implemented.

But the service class `DefaultTodoService` is empty, and the repository class is missing.

Your tasks are the following:
- Add `com.epam.training.todolist.dataaccess.documents.Todo` type which is the MongoDB document representation
  of the Todo entity. Add MongoDB annotations to the class. Do not forget about the id field.
- Implement `DefaultTodoService` class. Please note that the service operations receive and return `Item` type,
  and the stored type is `Todo`. So you need to make sure of the data transformation.
- Add the repository class `com.epam.training.todolist.dataaccess.TodoRepository` to the project, which should be a `MongoRepository`.

Reading:
- Mapping annotations: https://docs.spring.io/spring-data/mongodb/reference/mongodb/mapping/mapping.html#mapping-usage
- MongoRepository: https://docs.spring.io/spring-data/mongodb/reference/mongodb/repositories/repositories.html


## Infrastructure

The application needs a running MongoDB server. The suggested way to run it is via Docker.
So that the MongoDB server will be isolated, some basic configuration is provided, and it will be easy to remove.
Read instructions in the `docs/install.md` file.
