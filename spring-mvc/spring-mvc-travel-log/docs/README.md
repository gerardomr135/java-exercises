# Introduction

The goal of the exercise is to practice Spring MVC framework in a way that it was designed:
to implement a web application in Java that generates dynamic HTML.
Nowadays software projects follow a different approach:
the backend provides data and the frontend component is responsible for rendering the data.
Still, for learning the framework it is relevant to implement such a traditional Spring MVC application.

- Implement handler methods for the various HTTP methods
- Accept parameters that the browser sends: request parameters, form data, etc
- Populate model objects with ModelAttribute for the view to render
- Return the appropriate response: either view name, forward, or redirect response
- Validate the submitted data and handle errors using BindingResult 

The project is using Thymeleaf as view technology.
Students are not required to know or learn Thymeleaf. The thymeleaf files are provided.

For reference, please read here: [Thymeleaf Tutorial: Thymeleaf + Spring](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html)


# Application Domain

The domain that this application is built on is travel.

Travel captures the user's past travelling experiences. It has the following attributes:

- id: generated value for identification, example: **4**
- name:the name of the place that was visited, example: **Tower Bridge**
- location: location of the place, example: **London, United Kingdom**
- visitedAt: date that the user visited the place, example: **2025-01-15**
- category: the category of the place that was visited (enumeration), example: Architecture
- review: user opinion about the place and suggestions

The project contains Java classes to represent Travelling and Category and also generates example data.

See: `com.epam.training.travel.domain` and `com.epam.training.travel.data` packages.


# Application Functionality

The application can be used to:
- Display the list of the places that the user visited.
- Add new places
- Update reviews.

Please look at the files in html folder

- `travelling-list.html`, please open the file int browser. It shows how the list view should look like.
- `travelling-details.html`, this page can be used to update reviews for the given Travelling.


# Implementation

The project is a Spring Boot application that uses Thymeleaf as a template engine.
It contains several classes already to support quick implementation.

This section contains step-by-step instructions to implement the application.


## Home Controller

Users usually navigate to the root path of the application.
If there is no mapping defined for that url, the application would show an error.
Instead of showing an error, a better approach is to redirect the browser to the main page:
the Travelling list page.

### Steps:

1. Create a new controller class `HomeController` in the `com.epam.training.travel.controller` package.
   - Implement a handler method ("/" path, **get** mapping), that returns a redirect response:
     "redirect:/travelling-list"
2. Create a new controller class `TravellingListController`
   - Implement a handler method ("/travelling-list" path, **get** mapping), that returns the view name:
     "travelling-list".
3. Copy `travelling-list.html` and `menu.html` files from `html` folder to `src/main/resources/templates` folder.

### Testing

- Navigate to [http://localhost:8080](http://localhost:8080)
- Your browser should be redirected to http://localhost:8080/travelling-list 
  and display Travelling list page (currently static content).


## Travelling List Page

The next step is to make the travelling list content dynamic.
TravellingListController should populate the model with a list of Travelling objects
that the view can render in the table.

### Steps:

1. Create a `ModelAttribute`, name must be "travellingList".
   - Use `TravellingRepository` to query data and return in the model.
   - The objects in the model should be of type `List<TravellingModel>`.Use
     You can use `TravellingConverter` to convert `Travelling` to `TravellingModel`.

2. Change `travelling-list.html`
   - Remove static content and uncomment the thymeleaf code that renders the list.

### Testing
- When you navigate to the application, it should display dynamic content
  as defined by `InMemoryTravellingRepository`.


## Error handling

If any error or exception happens in the application,
Spring MVC displays a default error page "Whitelabel Error Page".

We would like to display an application-specific page.

### Steps

1. Exception handler
   - Create a handler method that catches all Exceptions. It should return "error" (view name).

2. Error view
   - Copy `error.html` from `html` folder to `src/main/resources/templates` folder.

### Testing
- Error page should be displayed in case of exceptions.


## Create Travelling

The application should allow users to add new Travelling objects.

### Steps

1. Modify `TravellingListController`
   - Add one more `ModelAttribute`, name must be "newTravelling", return a new `TravellingModel` instance. 
   - Implement a handler method ("/create-travelling" path, **post** mapping),
     that accepts `Travelling` object as a parameter.
     Specify also `BindingResult` as a parameter to handle validation errors.
     Save the `Travelling` object in the repository only if there are no errors.
     Make sure you also accept RedirectAttributes parameter and add success message.
   - The return value should be the view name (in case of error)
     and a redirect response to the same page to avoid form resubmission.
2. Modify `travelling-list.html`
   - Take `create-travelling.html`, copy the content inside `<div class="container">`,
     and paste it into `travelling-list.html` file below the table. See `<!-- Insert Form here -->`.
3. Modify `TravellingModel`
   - Add validation annotations to the fields: name, location, visitedAt, category.
     None of these fields can be empty.

You can make the validation errors visible on the page by adding the following:

```html
    <ul>
        <li th:each="e : ${#fields.detailedErrors()}" th:class="${e.global}? globalerr : fielderr">
            <span th:text="${e.global}? '*' : ${e.fieldName}">The field name</span> |
            <span th:text="${e.message}">The error message</span>
        </li>
    </ul>
```

### Testing

You should be able to create Travellings and see the created data in the list.


## Update Travelling

The application should allow users to view the details of a Travelling and write or update the review.

### Steps

1. Create a new controller class, `TravellingDetailsController`
   - Take inspiration from `TravellingListController`, creation logic
   - Model name must be "travelling".
   - Each method (handler methods and ModelAttribute methods) should accept
    `@RequestParam("travellingId") long travellingId` parameter

2. Copy `travelling-details.html` from `html` folder to `src/main/resources/templates` folder.
  - Remove static content and uncomment the form.

### Testing

The details of the selected Travelling should be displayed in the form.
The user should be able to write and update the review.
