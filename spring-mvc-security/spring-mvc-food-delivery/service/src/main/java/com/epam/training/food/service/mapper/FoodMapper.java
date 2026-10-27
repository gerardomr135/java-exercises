package com.epam.training.food.service.mapper;

import com.epam.training.food.domain.Cart;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.domain.OrderItem;
import com.epam.training.food.model.CartModel;
import com.epam.training.food.model.FoodModel;
import com.epam.training.food.model.OrderItemModel;
import com.epam.training.food.model.OrderModel;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class FoodMapper {
    public FoodMapper() {}

    public static FoodModel toFoodModel(Food food) {
        FoodModel foodModel = new FoodModel();

        foodModel.setId(food.getId());
        foodModel.setName(food.getName());
        foodModel.setCalorie(food.getCalorie());
        foodModel.setDescription(food.getDescription());
        foodModel.setPrice(food.getPrice());

        return  foodModel;
    }

    public static Food toFood(FoodModel foodModel) {
        Food food = new Food();

        food.setId(foodModel.getId());
        food.setName(foodModel.getName());
        food.setCalorie(foodModel.getCalorie());
        food.setDescription(foodModel.getDescription());
        food.setPrice(foodModel.getPrice());

        return  food;
    }

    public static OrderModel toOrderModel(Order order) {
        OrderModel orderModel = new OrderModel();

        orderModel.setId(order.getId());
        orderModel.setPrice(order.getPrice());
        orderModel.setTimestampCreated(order.getTimestampCreated().format(DateTimeFormatter.ISO_DATE_TIME));
        orderModel.setOrderItemModels(order.getOrderItems().stream().map(FoodMapper::toOrderItemModel).toList());

        return orderModel;
    }

    public static OrderItemModel toOrderItemModel(OrderItem orderItem) {
        OrderItemModel orderItemModel = new OrderItemModel();

        orderItemModel.setId(orderItem.getId());
        orderItemModel.setFoodModel(toFoodModel(orderItem.getFood()));
        orderItemModel.setPieces(orderItem.getPieces());
        orderItemModel.setPrice(orderItem.getPrice());

        return orderItemModel;
    }

    public static OrderItem toOrderItem(OrderItemModel orderItemModel) {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(orderItemModel.getId());
        orderItem.setFood(toFood(orderItemModel.getFoodModel()));
        orderItem.setPieces(orderItemModel.getPieces());
        orderItem.setPrice(orderItemModel.getPrice());

        return orderItem;
    }

    public static Cart toCart(CartModel cartModel) {
        Cart cart = new Cart();

        cart.setOrderItems(cartModel.getOrderItemModels().stream().map(FoodMapper::toOrderItem).toList());
        cart.setPrice(cartModel.getPrice());

        return  cart;
    }
}
