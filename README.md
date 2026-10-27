# Java Exercises

A collection of Java exercises and course follow-alongs completed over time (EPAM training tasks, LinkedIn Learning courses and personal experiments). Each folder is an independent project, most with its own `README.md` describing the task.

Most projects are Maven based. Build and test any of them with:

```
cd <project>
mvn clean test
```

Projects with a `mvnw` file can use `./mvnw` instead of a local Maven installation.

## Contents

1. [Core Java and OOP](#1-core-java-and-oop)
2. [Concurrency and multithreading](#2-concurrency-and-multithreading)
3. [Streams and functional style](#3-streams-and-functional-style)
4. [Data formats (XML, JSON, CSV)](#4-data-formats-xml-json-csv)
5. [Databases: JDBC, JPA and NoSQL](#5-databases-jdbc-jpa-and-nosql)
6. [Design patterns](#6-design-patterns)
7. [Clean code and unit testing](#7-clean-code-and-unit-testing)
8. [Build tooling: Maven](#8-build-tooling-maven)
9. [Spring Framework](#9-spring-framework)
10. [Web: Spring MVC, REST and security](#10-web-spring-mvc-rest-and-security)
11. [Microservices](#11-microservices)
12. [Messaging: RabbitMQ](#12-messaging-rabbitmq)
13. [AI and LLMs from Java](#13-ai-and-llms-from-java)
14. [Indirect topics](#14-indirect-topics)

---

## 1. Core Java and OOP

| Project | What it covers |
|---|---|
| `oop-concepts` | Abstract classes, interfaces, nested (static and inner) classes and shadowing, checked and unchecked exceptions, `String` handling, collections (`List`, `Set`, `HashMap`), mocking with Mockito |
| `oop-zoo` | Inheritance and polymorphism: zookeepers feed animals, each subclass has its own behaviour behind a common type |
| `oop-food-delivery` | SOLID, especially dependency inversion: abstract readers, a `DataStore` interface, a service that depends on abstractions |
| `ISBNValidator` | String and number manipulation, validation logic, JUnit 5 |
| `meet-autocode-exercise` | The first "Hello, Autocode!" task used to learn the exercise platform |

**Topics:** classes and objects, encapsulation, inheritance, polymorphism, abstraction, interfaces, abstract classes, nested classes, exceptions, collections, generics, `equals` / `hashCode`, SOLID.

## 2. Concurrency and multithreading

| Project | What it covers |
|---|---|
| `incdecthreads-exercise` | Creating threads by extending `Thread` and implementing `Runnable` |
| `spam-exercise` | `Thread` methods, sleeping, interrupting, stopping threads on a signal, a custom `InputStream` |
| `matrix-exercise` | Thread pools (`ExecutorService`) to find a maximum in parallel, compared with a single thread |
| `blocking-queue-exercise` | Producer/consumer with `BlockingQueue`, avoiding explicit synchronization (text preparation pipeline) |
| `wallet-exercise` | Locks and advanced synchronizers to guard shared accounts in a concurrent wallet |
| `Threads` | Multi-module course project: `ThreadControl` (join, priority, interrupt, states, uncaught exceptions), `ThreadPool` (executors, `Callable`, `Future`), `Synchronization` (`volatile`, deadlock), `ConcurrentCollections` (producer/consumer, file search tasks), `AdvancedSynchronizers` (`CountDownLatch`, fork/join) |

**Topics:** thread lifecycle, `Runnable` vs `Callable`, executors, `Future`, locks, `synchronized`, `volatile`, deadlock, `CountDownLatch`, concurrent collections, producer/consumer, fork/join, race conditions, measuring execution time.

## 3. Streams and functional style

| Project | What it covers |
|---|---|
| `maxbystreams-exercise` | Sequential and parallel `Stream` API, fork/join, timing both approaches |
| `data-format-json-products` | Filtering, grouping and sorting a product list with streams and `Collectors` |

Streams are also used throughout the Spring, JPA and food-delivery projects.

**Topics:** `Stream`, `map` / `filter` / `reduce`, `Collectors`, `Optional`, parallel streams, lambdas, method references.

## 4. Data formats (XML, JSON, CSV)

| Project | What it covers |
|---|---|
| `data-format-person` | Reading persons from a CSV file and writing a phonebook as XML with JAXB |
| `data-format-json-products` | Reading and querying JSON with Jackson `ObjectMapper` |
| `XMLInJava` | Parsing customer data from XML and JSON and timing each approach (the sample data files are not included) |

**Topics:** JAXB annotations (marshalling / unmarshalling), Jackson (`ObjectMapper`, annotations, `JavaTimeModule`), CSV parsing, XSD, file I/O, character encodings.

## 5. Databases: JDBC, JPA and NoSQL

| Project | What it covers |
|---|---|
| `getting-employee-info-master` | JDBC CRUD on `departments` and `employees`, tested against Derby and PostgreSQL |
| `JDBC` | DAO / DTO pattern with PostgreSQL: customers, orders and sales people (`SQL-Scripts` holds the schema and a Docker setup guide) |
| `SQL-Scripts` | SQL scripts and notes for the JDBC project |
| `jpa` | Spring Data JPA course: entity manager, associations, a university model |
| `jpa-playground` | Free experimentation with JPA and Hibernate on H2 |
| `persistence-food-delivery` | Food delivery with a persistence module using Spring Data JPA |
| `nosql-todo-list` | A todo list on MongoDB with Spring Data MongoDB and object-document mapping |

**Topics:** `Connection` / `PreparedStatement` / `ResultSet`, transactions, SQL joins, DAO pattern, ORM, `@Entity`, `@OneToMany` / `@ManyToOne`, repositories, derived queries, H2, PostgreSQL, MongoDB, Docker for databases.

## 6. Design patterns

| Project | Pattern |
|---|---|
| `design-patterns-book` | **Composite**: the Bible modelled as testaments, books, chapters and verses |
| `design-patterns-smart-home` | **Observer**, **Command** (with a command factory), **Strategy** (coffee maker), **Adapter** (legacy heating system), plus a controller |

## 7. Clean code and unit testing

| Project | What it covers |
|---|---|
| `clean-code-unclean-function` | Refactoring an unreadable function while keeping its tests green |
| `clean-code-hangman` | Clean code course follow-along, including a small Spring Cloud sample (config server, Eureka, room/guest reservation services) |
| `unit-testing-login` | Unit tests with Mockito for a command-line login application |
| `unit-testing-sms` | Parameterized tests for old mobile phone keypad text input |

**Topics:** meaningful names, small functions, refactoring, JUnit 5, parameterized tests, Mockito mocks and stubs, AssertJ and Hamcrest assertions, test-driven development, ArchUnit and Awaitility in the concurrency tasks.

## 8. Build tooling: Maven

| Project | What it covers |
|---|---|
| `build-maven-food-delivery` | Splitting an application into `application`, `service` and `persistence` modules with a parent pom |
| `food-delivery` family below | Reuses the same multi-module layout |

**Topics:** pom structure, dependencies and scopes, parent poms and multi-module builds, plugins, the Maven wrapper (`mvnw`), the Spring Boot Maven plugin.

## 9. Spring Framework

| Project | What it covers |
|---|---|
| `LearnSpringFramework` | Spring basics from a course: beans and the application context |
| `library-master` | Dependency injection with print and e-book configurations |
| `spring-core-building-alarm` | Spring Core with Jackson and SLF4J |
| `spring-core-food-delivery` | Food delivery wired with Spring Core |
| `spring-boot-food-delivery` | The same application on Spring Boot |
| `spring-aop-food-delivery` | Aspect-oriented programming with Spring AOP |

**Topics:** inversion of control, dependency injection, `ApplicationContext`, `@Component` / `@Service` / `@Configuration` / `@Bean`, scopes, `@Autowired`, `@Value` and properties, aspects, advice and pointcuts, Spring Boot auto-configuration and starters, logging.

## 10. Web: Spring MVC, REST and security

| Project | What it covers |
|---|---|
| `spring-mvc` | Controllers, Thymeleaf views, a REST todo API, a travel log, DTO mapping with ModelMapper, a serving-web-content guide |
| `spring-mvc-security` | Food delivery web app with Spring Security and an OpenAPI-generated API |
| `springboot-linkedin` | Spring Boot course: `room-clr` (command-line runner) and `room-web-app` (MVC and REST controllers, services, repositories, security) |
| `movie-client-copilot-task` | A Thymeleaf movie web app built with GitHub Copilot's help |

**Topics:** MVC pattern, `@Controller` / `@RestController`, request mapping, model and views, Thymeleaf templates, form handling and validation, HTTP verbs and status codes, REST design, OpenAPI / Swagger, authentication and authorization, password encoding, DTOs vs entities, Spring Boot DevTools, H2 console.

## 11. Microservices

| Project | What it covers |
|---|---|
| `microservices-lab` | Spring Cloud sample: API gateway, config server, discovery server, product and order services |
| `microservices-web-shop` | A web shop split into authentication, order, shipping, key-generator and discovery services, with OpenAPI-generated clients and JWT |

**Topics:** service decomposition, service discovery (Eureka), centralized configuration, API gateway, inter-service communication, JWT and key pairs, contract-first APIs with OpenAPI Generator, Actuator health checks, validation.

## 12. Messaging: RabbitMQ

| Project | What it covers |
|---|---|
| `rabbitmq-hello-world` | Producer and consumer on a quorum queue, with a Docker Compose file |
| `messaging-thumbnail-image-task` | Work queue pattern with Spring AMQP: a producer submits images and a consumer generates thumbnails |

**Topics:** message brokers, queues, producers and consumers, acknowledgements, work queues, asynchronous processing, JSON message conversion, Docker Compose.

## 13. AI and LLMs from Java

| Project | What it covers |
|---|---|
| `restaurant-chat-task` | A chatbot for the food delivery domain with LangChain4j (Ollama and OpenAI) |
| `sport-events-langchain4j-task` | Information extraction: turning free text about a sport event into a structured object |
| `prompt-exercises-task` | Prompt engineering exercises: summarization, classification and few-shot technique |

**Topics:** chat models, prompts and system messages, few-shot prompting, structured output, information extraction, running local models with Ollama, keeping API keys out of source code.

## 14. Indirect topics

These are not the focus of any single exercise but were needed to complete them.

**Java language and library**
- Generics, varargs, enums, records, `var`, text blocks and the Java version differences between projects (11, 17, 21)
- `java.time` (dates and durations), and `java.nio.file` and streams for file I/O
- Resource handling with try-with-resources and suppressed exceptions
- Annotations and a little reflection (used heavily by Spring, JPA, Jackson and Lombok)
- Logging with SLF4J, Logback and slf4j-simple

**Libraries**
- **Lombok:** `@Data`, `@Builder`, `@Slf4j` to reduce boilerplate
- **Jackson, JAXB:** serialization behind the REST and data-format exercises
- **JUnit 5, Mockito, AssertJ, Hamcrest, Awaitility, ArchUnit:** the testing toolbox
- **Thumbnailator:** image resizing in the messaging task
- **Spoon, BCEL, Byte Buddy:** code analysis libraries used by the exercise checkers

**Tools and environment**
- Git and GitHub, `.gitignore` for build output and IDE files
- Docker and Docker Compose for PostgreSQL, MongoDB and RabbitMQ
- IntelliJ IDEA and WSL
- Markdown for documentation

**Software engineering concepts**
- Layered architecture (controller, service, repository), separation of concerns, DTOs
- Dependency inversion and programming to interfaces
- Immutability and thread safety
- Configuration through properties files and environment variables
- Relational modelling (keys, joins, one-to-many) and ORM mapping
- HTTP basics, JSON, statelessness, CORS and CSRF when using Spring Security
- Timing and benchmarking (comparing sequential and parallel code)
- Prompt design and the limits of LLM output

## Notes

- Some folders come from external courses or exercise templates, so their code follows the original task structure.
- Build output (`target/`) and IDE folders are not committed.
- `XMLInJava` expects sample data files that are not part of this repository.
