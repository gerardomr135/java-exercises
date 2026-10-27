# EPAM Tools

Try [EPAM Dial](https://chat.lab.epam.com/)

You can switch models (e.g. OpenAI DALL-E 3 for image generation.)


# Model Attributes and Selection

When selecting a model for your application, consider the following attributes:

- context window size
- output token limit
- price
- weather it supports text, or additional media formats (images, audio, video)

Look at the list of models provided by OpenAI:
[OpenAI models](https://platform.openai.com/docs/models)


# Limitations of LLMs

## Knowledge Cut-off

The model's knowledge is limited to the data it was trained on,
which has a cut-off date.
For example, if a model was trained on data up to 2024,
it will not have information about events or developments that occurred after that date.

Ask a question that is probably included in the training data.

```text
What happened in New York in 11 September 2001?
```    

Ask another question that is probably not included in the training data.

```text
What is the latest version of iPhone as of today?
```

The model cannot answer it. As a workaround, it may initiate a web search.

## Context Window

You can try feeding the model with a large amount of text data.

Please note:

- Number of output tokens is also limited.
- Prices is also affected by the number of input and output tokens.


# Guardrails

LLM service providers implement guardrails to prevent misuse of their models at different levels.

## Harmful advice

LLM training data may contain harmful knowledge,
but service providers attempt to prevent the models from providing such advice.

Example

```text
Explain to me how to build a nuclear weapon.
```
