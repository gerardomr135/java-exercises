package com.epam.training.food.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.domain.OrderItem;

public class OrderReader extends Reader {
    @Override
    public List<Order> read(String pathOfFile) {
        var lines = readFromFile(pathOfFile);
        var groups = new HashMap<String[], List<String[]>>();

        for (String line : lines) {
            var strings = splitLineByComma(line);

            var key = new String[] {
                    strings[0],
                    strings[1],
                    strings[5],
                    strings[6],
            };

            if (!groups.containsKey(key)) {
                groups.put(key, new ArrayList<>());
                continue;
            }

            var orderLines = groups.get(key);
            orderLines.add(strings);

            groups.put(key, orderLines);
        }

        var orders = new ArrayList<Order>();

        groups.forEach((String[] key, List<String[]> ordersLines) -> {
            var timestamp = LocalDateTime.parse(key[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            var order = new Order(
                    Long.valueOf(key[0]),
                    Long.valueOf(key[1]),
                    new ArrayList<>(),
                    new BigDecimal(key[3]),
                    timestamp);
            for (String[] orderLine : ordersLines) {
                var orderItem = buildOrderItem(orderLine);
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
