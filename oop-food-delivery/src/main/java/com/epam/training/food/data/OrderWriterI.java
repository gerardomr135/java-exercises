package com.epam.training.food.data;

import java.util.List;

import com.epam.training.food.domain.Order;

// Interface to decouple the OrderWriter class from 
// the FileDataStore class and use dependency inversion.
public interface OrderWriterI {

    void writeOrders(List<Order> orders, String outputFile);

}