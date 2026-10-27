# Introduction

The goal of this exercise is to practice building a Spring Boot web application with Copilot's help.

Focus on learning how to use Copilot. The application's features are secondary.
Your main objective is to gain experience with Copilot,
so it's fine if the application is only **partially** completed.

## Application Description

This project includes a high-level requirements file, `docs/high-level-requirements.md`, which
describes the application's features and technical requirements.

The application is a simple movie search tool integrated with the IMDb movie database.
It allows users to search for movies by title and view details, including ratings.

Note: The API does not require an API key.

# Using Copilot

Tips for using Copilot effectively:

- Start a new chat session for each step.
- **Chat Mode**: Choose between **Ask**, **Edit**, or **Agent** modes.
  Usually, start with Ask mode and switch to Edit or Agent mode if you agree with the proposed changes.
- **Context**: Add the required files to the context.
- **Review** the generated code carefully. Copilot may make mistakes.
  Make sure the content matches what you would implement **yourself**!
  You are responsible for the generated code.
- Commit your changes to Git after each step.

# Preparation

Make sure you have a Copilot license. Add the plugin to your IDE and activate it.

## Instructions File

You will find a Copilot instructions file, `copilot-instructions.md`, in the project.
Configure it in Copilot.

This is the initial file. If Copilot generates code that does not meet your quality or style requirements,
add more rules to the instructions file.

# Planning

This is the planning phase. Your goal is to have Copilot create a plan for you.

Copilot chat:
- Chat mode: Ask
- Context: `high-level-requirements.md`

Ask Copilot to create a high-level design:
- Identify the main software components, each with a clear responsibility.
- Specify which components depend on others.
- List Maven dependencies to add to the project.
- List files to create and their structure.
- Note any configuration values the project should have.

Create a plan file:
- Name: `plan.md` (Markdown)
- Review Copilot's output, adjust if needed, and add the content to the plan file.

# Create the Project

Start a new chat session:
- Mode: Agent
- Context: The `plan.md` file you created in the previous step.

Ask Copilot to create the project files:
- `pom.xml`
- `application.yml`
- Spring Boot main class
- Folder structure as defined in the plan
- Classes as defined in the plan, without implementation

Review the generated files:
- Copilot may make mistakes. Sometimes it generates content (e.g., `pom.xml`) in the wrong file.
- Fix minor issues as needed.
- If there are major problems, roll back the files, improve your instructions, and generate again.

Initialize the project:
- Load the project as a **Maven project** in your IDE.
- Compile, and commit if successful.

# Implementation

Implement the application features step by step.

Use a top-down approach:
- Start with user input handling in the view and controller layers.
- Continue with the service layer and REST client.
- Finish by displaying data in the views.
- Repeat these steps for each new feature.

# Hints

Below are some tips gathered while implementing the application.

Always add the high-level requirements file, `high-level-requirements.md`, to the context.
It will help Copilot at each step.

## Maven Build

Check that Copilot has added the `spring-boot-maven` plugin to the `pom.xml` file,
and is using the correct version of Spring Boot.

## Search View

You need two handler methods in the controller for the search view:
- A `GET` mapping method to display the search view.
- A `POST` mapping method to handle the search form submission.

## REST Client

Implementing the REST client is likely the most challenging part.
- API specification: https://imdbapi.dev/
- Example responses are in the `docs` folder: `movie-search-response-example.json`
  and `movie-details-response-example.json`.
- You need a `RestTemplate` bean in the Spring context to call the API.

Pay attention to Java value objects and JSON mapping.
If you define a Java class (e.g., `MovieDto`) to receive the search response objects,
it may have fields such as:
- id
- title
- year

The JSON property names may differ (e.g., `primaryTitle`). Add the necessary `@JsonProperty` annotations
to the fields.

When processing the `/search/title` endpoint result, note that the result is not an array but an object
containing a field "titles", which is an array of movie objects.
Create a Java class, e.g., `MovieSearchResultDto`, to represent the entire result object.

## View

If you add a data model to the context, Copilot can generate the Thymeleaf view template files.

In my case, it generated a template that joined collection values into a string using commas.
The generated expressions were complex and did not work, so I asked it to generate an unordered list instead.

When the content is ready, ask Copilot to add Bootstrap CSS classes to the HTML elements to improve the appearance.
