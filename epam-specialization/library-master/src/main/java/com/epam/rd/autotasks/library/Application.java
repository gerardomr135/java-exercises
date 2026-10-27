package com.epam.rd.autotasks.library;

import com.epam.rd.autotasks.library.config.BookConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
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
    }
}
