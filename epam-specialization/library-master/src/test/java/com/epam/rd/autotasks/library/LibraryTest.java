package com.epam.rd.autotasks.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.rd.autotasks.library.config.AuthorConfig;
import com.epam.rd.autotasks.library.config.BookConfig;
import com.epam.rd.autotasks.library.config.PublisherConfig;

public class LibraryTest {

	@Test
	public void testAuthorConfiguration() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AuthorConfig.class)) {
			Author author = context.getBean(Author.class);
			assertNotNull(author, "Author should not be null");
			assertEquals("John Doe", author.getName(), "Author name should be \"John Doe\"");
		}
	}

	@Test
	public void testPublisherConfiguration() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				PublisherConfig.class)) {
			Publisher publisher = context.getBean(Publisher.class);
			assertNotNull(publisher, "Publisher should not be null");
			assertEquals("Spring Publishing House", publisher.getName(),
					"Publisher name should be \"Spring Publishing House\"");
			assertEquals("123 Spring St.", publisher.getAddress(), "Publisher address should be \"123 Spring St.\"");
			assertEquals(2000, publisher.getFoundedYear(), "Publisher founded year should be 2000");
		}
	}

	private static Stream<Arguments> provideTestConfigurations() {
		return Stream.of(Arguments.of("printBook", "Print", "The Adventures of Spring (Print Edition)"),
				Arguments.of("ebook", "E-book", "The Adventures of Spring (E-book Edition)"));
	}

	@ParameterizedTest
	@MethodSource("provideTestConfigurations")
	public void testLibraryManagementSystem(String bookBeanName, String expectedType, String expectedTitle) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BookConfig.class)) {
			Book book = context.getBean(bookBeanName, Book.class);
			assertNotNull(book, "Print book should not be null");
			assertEquals(expectedTitle, book.getTitle(), "Book title should be \"" + expectedTitle + "\"");
			assertEquals(expectedType, book.getType(), "Book type should be \"" + expectedType + "\"");

			assertNotNull(book.getAuthor(), "Print book author should not be null");
			assertNotNull(book.getPublisher(), "Print book publisher should not be null");
		}
	}
}