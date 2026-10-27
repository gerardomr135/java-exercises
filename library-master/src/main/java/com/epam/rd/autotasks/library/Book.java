package com.epam.rd.autotasks.library;

public class Book {
	private String title;
	private Author author;
	private Publisher publisher;
	private String type;

	public Book(String title, Author author, Publisher publisher, String type) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public Author getAuthor() {
		return author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public String getType() {
		return type;
	}
}