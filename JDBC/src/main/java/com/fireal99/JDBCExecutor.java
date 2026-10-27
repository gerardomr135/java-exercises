package com.fireal99;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCExecutor {
    public static void main( String[] args )
    {
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");

        try {
            Connection connection = databaseConnectionManager.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);
            findAllSortedAndPaged(customerDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findAllSortedAndPaged(CustomerDAO customerDAO) {
        customerDAO.findAllSorted(10).forEach(System.out::println);
        System.out.println();
        customerDAO.findAllPaged(10, 2).forEach(System.out::println);
    }

    public static void findOrdersByCustomerId(OrderDAO orderDAO) {
        List<Order> orders = orderDAO.findOrdersByCustomerId(789);
        orders.forEach(System.out::println);
    }

    public static void getOrderById(OrderDAO orderDAO) {
        Order order = orderDAO.findById(1195);

        System.out.println(order);
    }

    public static void deleteCustomer(CustomerDAO customerDAO) {
        long id = createCustomer(customerDAO);
        System.out.println("Inserted id: " + id);

        customerDAO.delete(id);
    }

    public static void updateCustomer(CustomerDAO customerDAO) {
        Customer customer = customerDAO.findById(1000);
        System.out.println(customer);
        customer.setFirstName("George");
        customer.setLastName("Richard");
        customerDAO.update(customer);
        System.out.println(customer);
    }

    public static void findById(CustomerDAO customerDAO) {
        Customer customer = customerDAO.findById(1000);
        System.out.println(customer);
    }

    public static long createCustomer(CustomerDAO customerDAO) {
        Customer customer = new Customer();

        customer.setFirstName("Han");
        customer.setLastName("Wang");
        customer.setEmail("email@email.com");
        customer.setPhone("123456789");
        customer.setAddress("address");
        customer.setCity("city");
        customer.setState("state");
        customer.setZipCode("zipcode");

        Customer newCustomer = customerDAO.create(customer);
        System.out.println(newCustomer);

        return newCustomer.getId();
    }
}
