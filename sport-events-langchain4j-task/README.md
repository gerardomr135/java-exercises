# Introduction

**Information Extraction** is a subfield of Natural Language Processing (NLP) that focuses on
automatically extracting structured information from unstructured text.

In this exercise, you will implement a Java application that takes a text describing a **sport event**
and, leveraging LLM capabilities, extracts relevant information such as the persons or teams involved, the score,
the date of the event, and the location.

# Reading

LLMs allow you to specify the required output structure as part of the prompt. In this case, we want the output
to be in **JSON** format. **LangChain4j** allows you to specify the format using a JSON schema.

Please read the following documentation page:
[Structured Outputs](https://docs.langchain4j.dev/tutorials/structured-outputs)

# Prerequisites

You need the following to successfully complete this exercise:
- An **API key** to interact with the LLM.
- Completion of the basic LangChain4j exercise and experience with the tool.

# The Task

Here is an example text describing a sport event:

```text
History is made as Eliud Kipchoge becomes first human to break the two-hour marathon barrier
Kenyan legend runs 1:59:40:2 in INEOS 1:59 Challenge
34-year-old proves No Human is Limited
Eliud Kipchoge proved No Human Is Limited when he became the first human in history to run a sub-two-hour marathon in the city of Vienna, Austria this morning. Kipchoge ran the 42.195KM distance in a remarkable time of 1:59:40:2.
The 34-year-old Kenyan’s landmark achievement began at 8:15AM CEST on the Reichsbrucke Bridge in Vienna in perfect weather conditions of around 9 degrees celcius and wind speeds between 0.5 - 1.5 m/s.
```

Expected output:

```json
{
    "event": "INEOS 1:59 Challenge",
    "sportType": "Marathon",
    "date": "2019-10-12",
    "player": "Eliud Kipchoge",
    "result": "1:59:40.2",
    "location": "Vienna, Austria"
}
```

# Write the Prompt

Before implementing the application, you need to write the prompt that will be used to extract the information.
Test the prompt in a chat interface.

# Implementation

You need to implement the following classes:
- A Java class that creates a `ChatModel` instance based on configurations in `application.yml`.
- A service class, based on the usage example in `ApplicationLogic`.
- A schema definition class.
- A converter class that converts the JSON response to `SportEvent`.

## Schema Definition Class

It should define a single method
that specifies the JSON schema
and returns an instance of `dev.langchain4j.model.chat.request.ResponseFormat`.

The JSON schema allows you to add **descriptions** to fields. Use descriptions to instruct the LLM what value it should set
in each field. E.g. "date of the event, e.g. 2023-10-15"

## Service Class

Make sure to create a `ChatRequest` object and call the `responseFormat()` method, passing the schema.

If the text is not related to sport events, the LLM will not be able to extract data. It will return a JSON object with empty fields.

You can check the result by calling the `isValid()` method of the converted `SportEvent`. 
Throw an **exception** in this case to indicate the error.

## Converter

You can use `ObjectMapper` to perform the conversion.

# Testing

Test the application by running it on different input files.

## Larger Context

Imagine that the service is part of an automatic data processing pipeline:
- It processes sport events.
- Before storing in a text database, it extracts key details to make the event searchable.
- To improve efficiency, the application may call the **batch** version of the LLM /completion endpoint.
