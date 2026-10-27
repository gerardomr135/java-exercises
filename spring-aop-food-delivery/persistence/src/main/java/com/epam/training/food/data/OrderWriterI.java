package com.epam.training.food.data;

import com.epam.training.food.domain.Order;

import java.util.List;

// Interface to decouple the OrderWriter class from 
// the FileDataStore class and use dependency inversion.
public interface OrderWriterI {

    void writeOrders(List<Order> orders, String outputFile);

}