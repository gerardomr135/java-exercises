package com.epam.training.order.apimapper;

import com.epam.training.order.apimodel.OrderAndPackageModel;
import com.epam.training.order.apimodel.OrderModel;
import com.epam.training.order.apimodel.PackageModel;
import com.epam.training.order.data.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderModelApiMapper {
    public OrderModel map(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.id(order.getId());
        orderModel.setDescription(order.getDescription());
        orderModel.setPrice(order.getPrice().doubleValue());
        return orderModel;
    }

    public OrderAndPackageModel map(Order order, com.epam.training.shipping.apimodel.PackageModel shippingPackageModel) {
        PackageModel packageModel = new PackageModel();
        packageModel.setId(shippingPackageModel.getId());
        packageModel.setStatus(shippingPackageModel.getStatus());
        packageModel.setExpectedShippingDate(shippingPackageModel.getExpectedShippingDate());

        OrderAndPackageModel orderAndPackageModel = new OrderAndPackageModel();
        orderAndPackageModel.setOrder(map(order));
        orderAndPackageModel.setPackage(packageModel);
        return orderAndPackageModel;
    }
}
