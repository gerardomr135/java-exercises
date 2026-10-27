package com.epam.training.food.data;

import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class FileDataStore implements DataStore {
    private final String inputFolderPath;
    private List customers;
    private List foods;
    private List orders;
    private OrderWriterI orderWriter;

    private static final String CUSTOMERS_FILENAME = "/customers.csv";
    private static final String FOODS_FILENAME = "/foods.csv";
    private static final String ORDERS_FILENAME = "/orders.csv";

    public FileDataStore(String inputFolderPath) {
        this.inputFolderPath = inputFolderPath;
    }

    /**
     * Constructs a FileDataStore instance.
     * 
     * Note: This constructor works with Dependency Inversion (D) to reduce code
     * coupling and at the same time dependency injection.
     * It is not used to keep unit tests intact.
     */
    public FileDataStore(String inputFolderPath, Reader customersReader, Reader foodsReader, Reader ordersReader,
            OrderWriterI orderWriter) {
        this.inputFolderPath = inputFolderPath;
        this.customers = customersReader.read(inputFolderPath + CUSTOMERS_FILENAME);
        this.foods = customersReader.read(inputFolderPath + FOODS_FILENAME);
        this.orders = customersReader.read(inputFolderPath + ORDERS_FILENAME);
        this.orderWriter = orderWriter;
    }

    public void init() {
        this.customers = new CustomerReader().read(inputFolderPath + CUSTOMERS_FILENAME);
        this.foods = new FoodReader().read(inputFolderPath + FOODS_FILENAME);
        // this.orders = new OrderReader().read(inputFolderPath + ORDERS_FILENAME);
        this.orders = new ArrayList<>();
        this.orderWriter = new OrderWriter();
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Food> getFoods() {
        return foods;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order createOrder(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public void writeOrders() {
        orderWriter.writeOrders(orders, inputFolderPath + ORDERS_FILENAME);
    }
}
