# Introduction

In this task you will create a simple Java application that interacts with the AI chat endpoint.

The application's functionality is related to the **Food Delivery** application that you had implemented
in the previous stage.

(If you did not complete the previous stage, do not worry.
The current application is a stand-alone application that does not depend on the previous one.)

**Chatbots** are a common use case for LLMs.
Users can ask questions in natural language, and the bot can respond with relevant information.
In this task, you will create a simple **restaurant chatbot**.

## Task Overview

**LangChain** is a framework for building applications with LLMs.
It provides tools and abstractions to make it easier to create applications that interact with LLMs.
LangChain is written for Python, but it is also ported to **Java** as **LangChain4j**.

In this task, you will:
- Study the documentation of LangChain4j to understand the basic concepts and tools it provides.
- Implement the application in multiple phases to incrementally add more features and complexity.
- Start with a simple chat application that can respond to user questions about food using
  a system prompt with data in its context.
  Later, add **chat memory**, use a higher-level solution (**AI Services**),
  and add **tools** that the AI can call to achieve the customer's goal.

## Prerequisites

Make sure you have the following:
- Prompt Engineering skills
- Completed the task: **Connect to LLM**

If you use OpenAI as the LLM provider, make sure you have an API key and
set it in the `OPENAI_API_KEY` environment variable.

# Study LangChain4j

As a first step, get familiar with the basic concepts of **LangChain4j** by reading the documentation:

[LangChain4j - Chat and Language Models](https://docs.langchain4j.dev/tutorials/chat-and-language-models)

Make sure to understand the following concepts from the documentation:
- `ChatModel`
- `PromptTemplate` and `Prompt`
- `SystemMessage`, `UserMessage` and `AIMessage`
- `ChatRequest`, `ChatResponse`


# Phase 1: Simple Chat Application

The template project already contains a few files:

- The `configuration` package contains Java classes to create a `ChatModel` Spring bean.
  You can easily switch between LLM providers by selecting
  one of the available values for the `model-provider` key in `application.yml`.
- The `domain` package contains the basic domain objects for the restaurant application, such as `Food`.
- The `data` package contains a simple in-memory repository of food items.
- The `ApplicationStarter` class is the main class to start the application.

## System Prompt

The system prompt consists of two parts:
- **Static**: instructions for the model on how to behave and respond to user questions.
- **Dynamic**: application specific data that the model can use to answer user questions.

Create a new file in the project resources folder named `system-prompt-template.txt`
and add the static part of the system prompt to it.

Use a placeholder `{{foods}}` in the system prompt to indicate where the dynamic data
(the list of food items) should be inserted. The variable will be resolved by your code at runtime.

Note: do not worry about the content of the prompt at this stage, you will refine it later.

## SystemMessageProvider

Create a new class `SystemMessageProvider`. It will be responsible for creating the system message by combining
the static template and the dynamic data.
- Load the system prompt template from the resource file.
- Add the list of foods as text to the system prompt by replacing the `{{foods}}` placeholder.

Hint:
- Implement some formatter to get a structured and formatted representation of the Food list.
- Use the `PromptTemplate` class from LangChain4j to resolve the variable in the system prompt template.

## Service

Create a new class `RestaurantChatService` (or any other name according to your preference).
It will be responsible for interaction with the LLM.

Inject the `ChatModel` and `SystemMessageProvider` into the service and implement a **single method**
that takes the user question parameter (text) and returns the model response (text).

## Chat User Interface

The application should have a console-based user interface.

That user interface should do the following:

1. Write a welcome message to the user.
   Explain what the user can ask about (available foods in the restaurant).
2. Iteratively do the following:
    - Read user input from the console.
    - Pass the user input to the `RestaurantChatService` and get the response.
    - Print the response to the console.
3. The application should exit when the user types the exit command, e.g. "**/quit**".

## Testing

Test your application and make sure it works functionally as expected.

Refine your prompt.
- The model may write too much information for user questions (e.g., all the ingredients).
- The model may respond to questions that are not relevant to the restaurant domain (e.g. "What is the weather today?").

## Monitoring

Configure logging when you create the `ChatModel` bean to log the requests and responses:
```java
.logRequests(true)
.logResponses(true)
```

Look at the messages in `logs/application.log` file to understand how **system**, **user** and **assistant** messages
are sent to the model and received from the model.
You will also see statistics about the **number of tokens** in the request and response.


# Phase 2: Chat Service and Chat Memory

The application that you have implemented in the previous phase does not have any memory.
The model does not remember the previous interactions with the user in the chat session.

See an example:

```text
Your question: What food can you offer that contains mushroom?
We offer Mushroom Risotto — it contains mushrooms.
Your question: Does it contain any allergen?
Which dish do you mean — Pasta al Pesto, Eggplant Parmigiana, Mushroom Risotto, or Margherita Pizza? I can list its allergens and ingredients.
```

In this phase you will improve the chat application with **memory**.

## Study LangChain4j

Study the following documentation in LangChain4j:

[LangChain4j - AI Service](https://docs.langchain4j.dev/tutorials/ai-services)

[LangChain4j - Chat Memory](https://docs.langchain4j.dev/tutorials/chat-memory)

## AI Service

You are going to use a higher-level abstraction called **AI Service** to implement the chat service.

Define an interface `RestaurantChatService` with a single method for interacting with the user, for example:

```java
String chat(String userMessage);
```

## System Message Provider

Change the `SystemMessageProvider` class to implement the following generic interface:

```java
Function<Object, String>
```

The apply method should return the system prompt text. You can ignore the method's argument (`chatMemoryId`).

You will pass this provider to the AI Service configuration in the next step.

## AI Service Configuration

Turn `RestaurantChatService` into a service interface with a single method.
(It should not contain any implementation.)

Configure an AI Service bean, by building from the following components:
- `RestaurantChatService` as a service interface
- `ChatModel`
- `SystemMessageProvider`
- Chat memory, e.g. `MessageWindowChatMemory.withMaxMessages(20)`

## Testing

Now the model should remember the previous interactions with the user.
Test the application again.

# Phase 3: AI Tools

If the model only chats with the user, the application will not achieve the business goal,
which is to get the user to **order food from the restaurant**.

Tools (Function Calling) are a way to allow the model to call external functions to achieve the user's goal.

## Study LangChain4j

Read the following documentation in LangChain4j to understand the concept of tools:

[LangChain4j - Tools (Function Calling)](https://docs.langchain4j.dev/tutorials/tools)

Focus on the following chapters:
- Introduction
- High Level Tool API, until InvocationParameters

## Add tools to the Application

We want to allow two operations:
- Add a food item to the order.
- Submit the order.

Define a new class in the domain package, `OrderItem`, that will represent an order of a food item with a given quantity.
It has two fields:
- `Food food` (reference to a `Food` object)
- `int quantity`

Define a class `RestaurantAssistantTools` (or other class that you find suitable) that will contain
the implementation of the two operations as public methods:
- `addToOrder(String foodName, int quantity)`
- `String submitOrder()`

The class should have a field to store `OrderItem`s.
The `addToOrder` method modifies the field by adding a new `OrderItem` to it
or modifying the quantity of an existing `OrderItem`.

`submitOrder` is incomplete and just for demonstration.
- The application does not have any real order processing logic including payment that should initiate
food preparation and order fulfillment.

Your implementation of `submitOrder` should be limited to:
- Order validation (e.g. check if it is not empty).
- return `"Success"` or a message with the error.
- Add some log statements to see that the method is called and what is the order content.

Add the necessary `@Tool` and `@P` (parameter) annotations to the tool methods.

Do not forget to **add a description** of the tool and its parameters in the annotations,
as it will help the model to understand when to call the tool and how to use it.

## Configure the tool

Create a bean of `RestaurantAssistantTools` class and add it to the AI Service configuration as a tool.

Do not forget to modify the system prompt to explain to the model:
- Its final goal: **submit the order**.
- It can **call tools** to achieve the user's goal.
- It needs some **confirmation instruction** from the user before calling `completeOrder` tool,
  to make sure the user really wants to do it.

## Testing

Make sure your application is working as expected:
- When the user adds some food to the order, the `addToOrder` method is called with the correct parameters.
- When the user completes the order, the `completeOrder` method is called and the order validation result is returned.

## Monitoring

Check the `logs/application.log` file to see when the tools are called and with what parameters.
