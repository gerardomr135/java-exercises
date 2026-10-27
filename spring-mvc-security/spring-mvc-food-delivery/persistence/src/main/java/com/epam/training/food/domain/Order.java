package com.epam.training.food.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    // the field price can be used later to add discounts, so it would differ from
    // the sum of food prices
    private BigDecimal price;

    private LocalDateTime timestampCreated;

    public Order() {
    }

    public Order(long customerId, List<OrderItem> orderItems, BigDecimal price, LocalDateTime timestampCreated) {
        this.customer = new Customer(customerId);
        this.orderItems = orderItems;
        this.price = price;
        this.timestampCreated = timestampCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(LocalDateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer)
                && Objects.equals(orderItems, order.orderItems) && Objects.equals(price, order.price)
                && Objects.equals(timestampCreated, order.timestampCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, orderItems, price, timestampCreated);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderItems=" + orderItems +
                ", price=" + price +
                ", timestampCreated=" + timestampCreated +
                '}';
    }
}
