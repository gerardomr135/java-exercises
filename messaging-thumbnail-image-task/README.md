# Introduction

In this exercise, you will implement a **Work Queue** messaging pattern.

# The Work Queue Pattern 

Reminder
- The system performs **resource-intensive** tasks that can be processed asynchronously in the background.
- There are two roles in this setup: the **Producer** component that defines the task,
  and the **Consumer** component that performs the task.
- The producer and consumer are decoupled by using **messaging infrastructure**:
  they communicate only through **messages**, not directly.
  Each message represents a task to be performed.
  The producer sends a message to the queue, and the consumer reads it from the queue and performs the task.
- The messaging infrastructure ensures **reliable messaging**,
  and supports **scalability** by allowing more consumer components to be added.


# The Exercise

This exercise is inspired by image storage systems.

The use case begins when an end user **uploads** an image to the server.
The image size may not be ideal for certain use cases, such as displaying images in a grid layout.
To solve the problem, after storing the images at their original size, the server starts background processing
to generate smaller **thumbnail** images.

Image:

![Image of Building](https://autocode.git.epam.com/java-exercise-public-artifacts/messaging/messaging-thumbnail-image-artifacts/-/raw/main/images/synagogue.jpg?ref_type=heads)

The generated thumbnail image:

![Thumbnail Image of Building](https://autocode.git.epam.com/java-exercise-public-artifacts/messaging/messaging-thumbnail-image-artifacts/-/raw/main/images/synagogue_thumbnail.jpg?ref_type=heads)

Thumbnail generation in this exercise is limited to a single machine: images must be copied to a directory in the file system.
The **Producer** application recursively scans the base directory and creates one message per image.
The **Consumer** application then processes the messages one by one,
generates thumbnail images, and saves them in the file system.

If the source directory is `C:\image-demo\images`, and the target directory is `C:\image-demo\thumbnails`,
the system will mirror the source directory structure into the target directory, as shown below:

```text
C:
└── image-demo
    ├── images
    │   └── 2024
    │       ├── 20240218_155002.jpg
    │       └── 20240218_155116.jpg
    └── thumbnails      
        └── 2024
            ├── 20240218_155002.jpg
            └── 20240218_155116.jpg
```

# System Components

The system consists of the previously mentioned **Producer** and **Consumer** applications,
along with a **RabbitMQ** server.

![System Components Diagram](https://autocode.git.epam.com/java-exercise-public-artifacts/messaging/messaging-thumbnail-image-artifacts/-/raw/main/images/System-components.png)

You can start the RabbitMQ server using Docker.
Navigate to the `docker` directory of the project and run the following command:

```bash
docker-compose up
```

RabbitMQ provides a management interface accessible at the following URL:
[http://localhost:15672/](http://localhost:15672/)

Username and password: guest / guest

Messages are formatted in JSON, which is a standard and human-readable format, making it easy to view in the RabbitMQ management console.

Note: In this simple setup, RabbitMQ does not require authentication,
as the producer and consumer connect as local processes.


# Producer Application Implementation

The producer application is built using Spring Boot.
Certain functionality has already been implemented, and the application can be started.

Before starting, please prepare the following test data:

- Create a directory and copy a few image files into it.
- Create another directory to store the thumbnail images.
- Ensure that these directories are configured in the application.yml file.

Example configuration for `application.yml`:

```yaml
image-directories-config:
  imageBaseDir: c:\\image-demo\\images
  thumbnailBaseDir: c:\\image-demo\\thumbnails
```

When you run the application, it will display log messages similar to the following:

```text
2025-04-21T19:08:01.957+02:00 DEBUG 25540 --- [           main] c.e.t.t.image.ImageFileScanner           : Image file: c:\image-demo\images\20240218_155002.jpg, thumbnail file: c:\image-demo\thumbnails\20240218_155002.jpg
2025-04-21T19:08:01.959+02:00 DEBUG 25540 --- [           main] c.e.t.t.image.ImageFileScanner           : Image file: c:\image-demo\images\20240218_155116.jpg, thumbnail file: c:\image-demo\thumbnails\20240218_155116.jpg
```

The producer application can already scan the image directories and define the thumbnail file names.

You will find instructions in the following chapters to implement a functional producer application.


# The Message Class

Create a new class `ThumbnailCreationRequest` in the `the com.epam.training.thumbnailgenerator.message` package.

Add the following properties to the class:
- `imageFilePath` (String)
- `thumbnailFilePath` (String)
- `createdAt` (OffsetDateTime) 

Find `processImageFile` method in the `ImageFileScanner` class
and create a new instance of `ThumbnailCreationRequest` class.

Populate its fields with the values available in the method.


# RabbitMQ Configuration

## Messaging Properties

Create a new class `MessagingProperties`.
This class should store the configuration properties from the `messaging-config` section in the `application.yaml` file.

Configuration Properties
- `queueName`
- `exchangeName`
- `routingKey`

Refer to the existing `ImageDirectoryProperties` class as an example of a configuration properties file.


## Beans Configuration

In this section, you will configure the dependencies required for connecting to the RabbitMQ message broker,
as well as beans to define the messaging objects (exchange, queue, binding). 

Add the following dependencies to the `pom.xml` file:

- `spring-boot-starter-amqp`: The Spring Boot starter for AMQP and RabbitMQ.
- `jackson-databind`: For handling JSON format, and `jackson-datatype-jsr310`: For Java 8 date and time support.

Create a new class named `RabbitMqConfiguration` in the `com.epam.training.thumbnailgenerator.configuration` package.

This class should define Spring beans using Java configuration for the following types:

- `DirectExchange`: Use the `exchangeName` property from `MessagingProperties` class
- `Queue`: Use the `queueName` property from `MessagingProperties` class
- `Binding`: Use `BindingBuilder` to create the binding, utilizing the two beans defined above
  and the `routingKey` property from the `MessagingProperties` class

Define an additional bean that extends Spring AMQP and acts as a converter
to transform the message object into JSON format before sending it to the broker.

```java
@Bean
public MessageConverter jsonMessageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return new Jackson2JsonMessageConverter(objectMapper);
}
```

## Create the Message Sender

The final step in defining the producer is to create a class responsible for sending messages to the RabbitMQ server.

Create a class called `MessageSender` in the `com.epam.training.thumbnailgenerator.sending` package.

It should be a Spring Bean with the following dependencies:
- `RabbitTemplate`: Used to send the messages.
- `MessagingProperties`: Used to access the configuration properties.

Define a `sendMessage` method that accepts a `ThumbnailCreationRequest` object as a parameter.
- Implementation: Call the `convertAndSend` method of `RabbitTemplate` with the appropriate parameters.


## Call the Message Sender

Add the `MessageSender` dependency to the `ImageFileScanner` class.

In the `processImageFile` method, call `sendMessage` method of the `MessageSender`, and pass the request object to it.


# Testing the Producer

When you start the producer application, it should send messages to RabbitMQ server's exchange.

Open the RabbitMQ management console and navigate to **Queues and Streams**.

You should see the number of messages, which should be a non-zero value:

![RabbitMQ Management, Queues](https://autocode.git.epam.com/java-exercise-public-artifacts/messaging/messaging-thumbnail-image-artifacts/-/raw/9f3d7eda79a3354bed72d5f4a1c9668f33a5f2ed/images/queue-monitor.png)

When you click on the queue name, the management console will display additional information.

Clicking the "**Get messages(s)**" button will display the message content. It should look like this:

![Content of a Message](https://autocode.git.epam.com/java-exercise-public-artifacts/messaging/messaging-thumbnail-image-artifacts/-/raw/9f3d7eda79a3354bed72d5f4a1c9668f33a5f2ed/images/message-content.png)


# Consumer Application Implementation

Create the `ThumbnailCreationRequest` class.

It should be identical to the one defined in the **Producer** application.

Create the `RabbitMqConfiguration` class.

It is similar to the class defined in the **Producer** application, but it defines only one bean:
the `MessageConverter` bean.


## The Listener

Create a new class called `MessageListener` in the `com.epam.training.thumbnailgenerator.listener` package.

This class must be a Spring bean with the following dependency: `ThumbnailProperties`.

Define a method called `processRequest` that accepts a `ThumbnailCreationRequest` object as a parameter.

```java
@RabbitListener(queues = "${messaging-config.queueName}")
public void processImage(ThumbnailCreationRequest thumbnailCreationRequest) {
}
```

Retrieve the `imageFilePath` and `thumbnailFilePath` properties from the request object
and create a `File` object for each of them.

Ensure that the parent directory of the thumbnail file path exists by the following API call:

```java
outputFile.getParentFile().mkdirs();
```


## Thumbnail Generation

The `application.yaml` file includes a section named `thumbnail-config`,
which allows you to configure the size of the generated thumbnail images.

Create A `ThumbnailProperties` class to map the configuration properties.
- This class should contain `width` and `height` fields, which are populated from the thumbnail-config section.

Use the `Thumbnailator` library to generate the thumbnail images. Add the following Maven dependency:

```xml
<groupId>net.coobird</groupId>
<artifactId>thumbnailator</artifactId>
<version>0.4.8</version>
```

Here is a code snippet to generate a thumbnail image:

```java
Thumbnails.of(inputFile)
    .size(thumbnailProperties.getWidth(), thumbnailProperties.getHeight())
    .keepAspectRatio(true)
    .toFile(outputFile);
```

Note
- Not all image types are supported by `Thumbnailator` library. Read here for additional information:
  [Which image formats are supported by Thumbnailator?](https://github.com/coobird/thumbnailator/wiki/FAQ#which-image-formats-are-supported-by-thumbnailator)



## Message Acknowledgement and Error handling

In order to implement reliable messaging, consumers must acknowledge messages to inform the message broker that
they have been processed successfully.
Auto acknowledgement will happen by default, if the message listener method completes without any error (exception).

So we must ensure that all exceptions are properly handled within the message listener method:

- The parent directory for the thumbnail may fail to be created due to security restrictions or other file system errors.
- An I/O error may occur when `Thumbnailator` attempts to write the image.
- Any other unexpected error during the image processing workflow.

If an exception is not handled, the message will be re-queued and delivered to the consumer again.

For the purposes of this exercise, you may simply log the error message to the console.

A best practice in production systems is to define a separate message queue — called a **Dead Letter Queue** (DLQ) — 
to collect messages that cannot be processed successfully.
However, implementing a DLQ is beyond the scope of this exercise.


# Testing the application

Once the consumer application is running, it should start consuming messages from the RabbitMQ server
and writing the corresponding thumbnail images to the file system.


# Handling Higher Load

When processing a large number of images, you can scale horizontally
by starting multiple instances of the consumer application.
This enables the system to handle the load more efficiently and reduce processing time.

Since the producer includes a timestamp in each message, you can monitor system performance
by measuring how long messages remain in the queue before being processed.
