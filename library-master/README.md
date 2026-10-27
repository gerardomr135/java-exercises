# Book Library

The goal of this task is to create a simple library management system and demonstrate how to manage different book configurations using Spring Dependency Injection.

Estimated workload of this exercise is *45 min.*

### Description

Design and implement a simple library management system to manage books, authors, and publishers in a digital library. The system should be able to handle multiple book configurations (e.g., print and e-books) and provide a clear separation of concerns between the library components.

### Details

1. Create a `Book` class to represent a book in the library with the following attributes: title, author, publisher, and type (e.g., print or e-book). The `Book` class should have appropriate getter and setter methods for its attributes.

2. Create an `Author` class to represent the author of a book, with a single attribute `name`. The `Author` class should have appropriate getter and setter methods for this attribute.

3. Create a `Publisher` class to represent a publisher, with the following attributes: `name`, `address`, and `foundedYear`. The `Publisher` class should have appropriate getter and setter methods for its attributes.

4. Follow best practices for Spring Dependency Injection by using constructor injection to inject dependencies (`Author` and `Publisher`) into the `Book` class.

5. Use Spring configuration classes to define and manage beans for the `Book`, `Author`, and `Publisher` classes. Utilize a separate configuration class for each component (i.e., `AuthorConfig`, `PublisherConfig`, and `BookConfig`):
    - `AuthorConfig` creates an author named "John Doe".
    - `PublisherConfig` creates a publisher named "Spring Publishing House" with the address "123 Spring St." and the founded year "2000".

6. Demonstrate the use of multiple Book configurations, such as print books and e-books, by defining separate beans for each configuration in the `BookConfig` class:
    - Create a book named "The Adventures of Spring (Print Edition)" type "Print" using a method named `printBook`.
    - Create a book named "The Adventures of Spring (E-book Edition)" type "E-book" using a method named `ebook`.

### Examples

Below is the Java code that initializes a Spring application context using the configuration classes, retrieves the beans for print books and e-books, and displays their details in the console.

```java
try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BookConfig.class)) {
	Book printBook = context.getBean("printBook", Book.class);
	Book ebook = context.getBean("ebook", Book.class);

	System.out.println("--- Print Book ---");
	System.out.println("Title: " + printBook.getTitle());
	System.out.println("Author: " + printBook.getAuthor().getName());
	System.out.println("Publisher: " + printBook.getPublisher().getName());
	System.out.println("Type: " + printBook.getType());
	System.out.println();
	System.out.println("--- E-book ---");
	System.out.println("Title: " + ebook.getTitle());
	System.out.println("Author: " + ebook.getAuthor().getName());
	System.out.println("Publisher: " + ebook.getPublisher().getName());
	System.out.println("Type: " + ebook.getType());
}
```
