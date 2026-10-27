# Introduction

In this exercise you will implement a RESTful To-do list and task management application.

The application is really simple, it does not have persistent storage,
it stores data in memory.

The application provides RESTful API to query and manage To-do items.

The application does not require user authentication.

The purpose of this project is to practice RESTful API development in java with Spring MVC


# Domain model

The Todo item class defines the following fields.

- `id`: number, unique identifier
- `title`
- `date`: the task should be done by
- `status`: enum values, execution status
- `priority`: enum values, how important it is

You find the `Todo` class already implemented in the template project in `com.epam.training.todorest.domain` package.


# Functionality

The application should define the following endpoints:

| Method | Url               | Description                              | HTTP status (success case) |
|--------|-------------------|------------------------------------------|----------------------------|
| GET    | `/api/todos`      | Query all To-do items                    | 200 (Ok)                   |
| GET    | `/api/todos/{id}` | Query single To-do item given by in path | 200 (Ok)                   |
| POST   | `/api/todos`      | Create To-do item                        | 201 (Created)              |
| PUT    | `/api/todos/{id}` | Update To-do item given by id in path    | 200 (Ok)                   |
| DELETE | `/api/todos/{id}` | Delete To-do item given by id in path    | 204 (No Content)           |

## Examples 

Example response of `GET /api/todos/{id}` :

```json
{
    "id": 1,
    "title": "Kids at school",
    "date": "2024-03-03",
    "status": "TO_DO",
    "priority": "HIGH"
}
```

`GET /api/todos` endpoint returns json array, the item format is the same as the result of the single item in previous example.

`POST /api/todos` endpoint requires the object to be passed as json body.
Id field is not required in this case, system will generate id for the created Todo item. 

## Error cases

- return HTTP status 404 (Not Found), when there is no item with the passed id.
- return HTTP status 400 (Bad Request), when the input structure does not conform specification

Error response:

```json
{
    "message": "Todo item with id 42 does not exist."
}
```


# Implementation

Please add and implement the following classes:

- `com.epam.training.todorest.services.DefaultTodoService`, implements `TodoService`

- `com.epam.training.todorest.web.TodoController`

Note: `TestDataCreator` will create test data automatically by calling the service create method.


## DefaultTodoService Specification

- It should have an id field to generate Todo identifier, initialized to 0.
- create() method should generate the next id and set in the Todo item.

Implement the other methods in an intuitive way.

## TodoController

Implement the RESTful operations.

Inject `TodoService` object and delegate all operations to it.

Do not forget to add exception handler for (at least) the following exception types:

- `NoSuchElementException` - thrown when Todo item not found for the given id.
- `HttpMessageNotReadableException` - thrown when conversion error happens during json processing.

TestDataCreator

# Testing

You can use browser to test GET operations.

Use Postman to test other operations.

- Do not forget to set **Content-Type** header parameter to *"application/json"* when you send json in body
  (in case of POST and PUT request)

Please run integration tests in `TodoControllerTest` and satisfy all of them.

Note: Autocode tester will contain more test cases than you find in the template project.

# Extra

If you would like to practice more, you can improve GET `/api/todos` endpoint by adding more query parameters
to further filter the returned Todo items.

Ideas:

GET `/api/todos?status=TO_DO&priority=HIGH`

**Enjoy!**
