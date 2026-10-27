package com.epam.training.food.data;

import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.domain.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderReader extends Reader {
    @Override
    public List<Order> read(String pathOfFile) {
        List<String> lines = readFromFile(pathOfFile);
        HashMap<String[], List<String[]>> groups = new HashMap<String[], List<String[]>>();

        for (String line : lines) {
            String[] strings = splitLineByComma(line);

            String[] key = new String[] {
                    strings[0],
                    strings[1],
                    strings[5],
                    strings[6],
            };

            if (!groups.containsKey(key)) {
                groups.put(key, new ArrayList<>());
                continue;
            }

            List<String[]> orderLines = groups.get(key);
            orderLines.add(strings);

            groups.put(key, orderLines);
        }

        ArrayList<Order> orders = new ArrayList<Order>();

        groups.forEach((String[] key, List<String[]> ordersLines) -> {
            LocalDateTime timestamp = LocalDateTime.parse(key[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Order order = new Order(
                    Long.valueOf(key[0]),
                    Long.valueOf(key[1]),
                    new ArrayList<>(),
                    new BigDecimal(key[3]),
                    timestamp);
            for (String[] orderLine : ordersLines) {
                OrderItem orderItem = buildOrderItem(orderLine);
                order.getOrderItems().add(orderItem);
            }

            orders.add(order);
        });

        return orders;
    }

    private OrderItem buildOrderItem(String[] strings) {
        return new OrderItem(
                new Food(strings[2], BigDecimal.ZERO, "", BigDecimal.ZERO),
                Integer.parseInt(strings[2]),
                new BigDecimal(strings[4]));
    }
}
